package ua.kucher.player.local.song

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import ua.kucher.player.core.common.coroutines.dispather.DispatcherProvider
import ua.kucher.player.database.AlbumEntity
import ua.kucher.player.database.AlbumEntityQueries
import ua.kucher.player.database.ArtisEntityQueries
import ua.kucher.player.database.ArtistEntity
import ua.kucher.player.database.SongEntity
import ua.kucher.player.database.SongEntityQueries
import ua.kucher.player.entity.Song
import ua.kucher.player.local.ArtworkCache
import ua.kucher.player.local.LocalStorageSource
import ua.kucher.player.local.mapToOneOrNull

@OptIn(ExperimentalCoroutinesApi::class)
internal class SongLocalSourceImpl(
    private val artworkCache: ArtworkCache,
    private val dispatcherProvider: DispatcherProvider,
    private val localStorageSource: LocalStorageSource,
    private val songEntityQueries: SongEntityQueries,
    private val artisEntityQueries: ArtisEntityQueries,
    private val albumEntityQueries: AlbumEntityQueries,
) : SongLocalSource {

    private fun songsFlow(
        songsFlow: Flow<List<SongEntity>>
    ): Flow<List<Song>> {
        val albumsFlow = albumEntityQueries.getAlbums()
            .asFlow()
            .mapToList(dispatcherProvider.io)

        val artistsFlow = artisEntityQueries.getArtists()
            .asFlow()
            .mapToList(dispatcherProvider.io)

        return combine(
            songsFlow,
            albumsFlow,
            artistsFlow
        ) { songs, albums, artists ->
            val albumsById: Map<Long, AlbumEntity> = albums.associateBy { album -> album.id }
            val artistsById: Map<Long, ArtistEntity> = artists.associateBy { artist -> artist.id }
            songs.map { song ->
                SongDto(
                    song = song,
                    album = albumsById[song.albumId],
                    artist = artistsById[song.artistId]
                ).toDomain()
            }
        }
    }

    override fun getSongById(id: Long) = songEntityQueries.getSongById(id)
        .asFlow()
        .mapToOne(dispatcherProvider.io)
        .flatMapLatest { song ->
            combine(
                artisEntityQueries.getArtistById(song.artistId).asFlow().mapToOneOrNull(),
                albumEntityQueries.getAlbumById(song.albumId).asFlow().mapToOneOrNull(),
            ) { artist, album ->
                SongDto(
                    song = song,
                    album = album,
                    artist = artist,
                ).toDomain()
            }
        }

    override fun getSongs(): Flow<List<Song>> = songsFlow(
        songEntityQueries.getSongs()
            .asFlow()
            .mapToList(dispatcherProvider.io)
    )

    override fun getSongsByAlbum(albumId: Long) = songsFlow(
        songEntityQueries.getSongsByAlbum(albumId)
            .asFlow()
            .mapToList(dispatcherProvider.io)
    )

    override fun getSongsByArtist(artistId: Long) = songsFlow(
        songEntityQueries.getSongsByArtist(artistId)
            .asFlow()
            .mapToList(dispatcherProvider.io)
    )

    override suspend fun fetchSongs(): Result<Unit> = runCatching {

        val songsInDevice = localStorageSource.getSongs()

        val mediaStoreIds = songsInDevice
            .map { song -> song.id }
            .toSet()

        val existingSongs = songEntityQueries
            .getSongs()
            .executeAsList()
            .associateBy { song -> song.id }

        val dbIds = existingSongs.keys
        val removedSongIds = dbIds - mediaStoreIds

        val newSongs = mutableListOf<SongEntity>()

        songEntityQueries.transaction {

            removedSongIds.forEach { id ->
                songEntityQueries.deleteSongById(id)
            }

            songsInDevice.forEach { song ->
                val existingSong = existingSongs[song.id]
                when {
                    existingSong == null -> {
                        songEntityQueries.insertSong(song)
                        newSongs.add(song)
                    }

                    existingSong.lastModified != song.lastModified -> {
                        songEntityQueries.updateSong(
                            title = song.title,
                            duration = song.duration,
                            uri = song.uri,
                            albumId = song.albumId,
                            artistId = song.artistId,
                            lastModified = song.lastModified,
                            id = song.id
                        )
                    }
                }
            }
        }

        coroutineScope {
            val deleteArtworkJobs = removedSongIds.map { songId ->
                async(dispatcherProvider.io) {
                    artworkCache.deleteArtworkFromCache(songId)
                }
            }
            val cacheArtworkJobs = newSongs.map { song ->
                async(dispatcherProvider.io) {
                    artworkCache.getAndCacheArtwork(song.id)?.also { artwork ->
                        songEntityQueries.insertArtwork(
                            id = song.id,
                            artwork = artwork,
                        )
                    }
                }
            }
            (deleteArtworkJobs + cacheArtworkJobs).awaitAll()
        }
    }
}