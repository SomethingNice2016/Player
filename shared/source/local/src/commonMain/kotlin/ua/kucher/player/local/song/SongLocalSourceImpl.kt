package ua.kucher.player.local.song

import app.cash.sqldelight.coroutines.asFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ua.kucher.player.database.AlbumEntityQueries
import ua.kucher.player.database.ArtisEntityQueries
import ua.kucher.player.database.SongEntity
import ua.kucher.player.database.SongEntityQueries
import ua.kucher.player.local.ArtworkCache
import ua.kucher.player.local.LocalStorageSource
import ua.kucher.player.local.mapToList
import ua.kucher.player.local.mapToOne
import ua.kucher.player.local.mapToOneOrNull

@OptIn(ExperimentalCoroutinesApi::class)
internal class SongLocalSourceImpl(
    private val artworkCache: ArtworkCache,
    private val localStorageSource: LocalStorageSource,
    private val songEntityQueries: SongEntityQueries,
    private val artisEntityQueries: ArtisEntityQueries,
    private val albumEntityQueries: AlbumEntityQueries,
) : SongLocalSource {

    override fun getSongById(id: Long) = songEntityQueries.getSongById(id)
        .asFlow()
        .mapToOne()
        .flatMapLatest { song ->
            combine(
                artisEntityQueries.getArtistById(song.artistId).asFlow().mapToOneOrNull(),
                albumEntityQueries.getAlbumById(song.albumId).asFlow().mapToOneOrNull()
            ) { artist, album ->
                SongDto(
                    song = song,
                    album = album,
                    artist = artist,
                ).toDomain()
            }
        }

    override fun getSongs() = songEntityQueries.getSongs()
        .asFlow()
        .mapToList { entity ->
            SongDto(
                song = entity,
                album = albumEntityQueries.getAlbumById(entity.albumId).executeAsOneOrNull(),
                artist = artisEntityQueries.getArtistById(entity.artistId).executeAsOneOrNull(),
            ).toDomain()
        }

    override fun getSongsByAlbum(albumId: Long) = songEntityQueries.getSongsByAlbum(albumId)
        .asFlow()
        .mapToList { entity ->
            SongDto(
                song = entity,
                album = albumEntityQueries.getAlbumById(entity.albumId).executeAsOneOrNull(),
                artist = artisEntityQueries.getArtistById(entity.artistId).executeAsOneOrNull(),
            ).toDomain()
        }

    override fun getSongsByArtist(artistId: Long) = songEntityQueries.getSongsByArtist(artistId)
        .asFlow()
        .mapToList { entity ->
            SongDto(
                song = entity,
                album = albumEntityQueries.getAlbumById(entity.albumId).executeAsOneOrNull(),
                artist = artisEntityQueries.getArtistById(entity.artistId).executeAsOneOrNull(),
            ).toDomain()
        }

    override suspend fun fetchSongs() = runCatching {
        val songsInDevice = localStorageSource.getSongs()
        val dbIds = songEntityQueries
            .getAllSongsIds()
            .executeAsList()
            .toSet()

        val mediaStoreIds = songsInDevice
            .map { it.id }
            .toSet()

        (dbIds - mediaStoreIds).forEach { id ->
            songEntityQueries.deleteSongById(id)
            artworkCache.deleteArtworkFromCache(id)
        }

        val existingSongs = songsInDevice.mapNotNull { song ->
            songEntityQueries.getSongById(song.id)
                .executeAsOneOrNull()
        }

        val newSongs = mutableListOf<SongEntity>()

        songsInDevice.forEach { song ->

            val existing = existingSongs.find { item ->
                item.id == song.id
            }

            when {
                existing == null -> {
                    songEntityQueries.insertSong(song)
                    newSongs.add(song)
                }

                existing.lastModified != song.lastModified -> {
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

                else -> Unit
            }
        }
        newSongs.map { song ->
            coroutineScope {
                launch {
                    artworkCache.getAndCacheArtwork(song.id)?.also { artwork ->
                        songEntityQueries.insertArtwork(artwork, song.id)
                    }
                }
            }
        }.joinAll()
    }
}