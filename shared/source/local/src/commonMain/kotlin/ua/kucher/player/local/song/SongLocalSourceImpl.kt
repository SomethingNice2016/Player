package ua.kucher.player.local.song

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.kucher.player.core.common.coroutines.dispather.DispatcherProvider
import ua.kucher.player.database.GetSongsByAlbumWithRelations
import ua.kucher.player.database.GetSongsByArtistWithRelations
import ua.kucher.player.database.GetSongsWithRelations
import ua.kucher.player.database.SongEntity
import ua.kucher.player.database.SongEntityQueries
import ua.kucher.player.entity.Song
import ua.kucher.player.local.ArtworkCache
import ua.kucher.player.local.LocalStorageSource


@OptIn(ExperimentalCoroutinesApi::class)
internal class SongLocalSourceImpl(
    private val artworkCache: ArtworkCache,
    private val dispatcherProvider: DispatcherProvider,
    private val localStorageSource: LocalStorageSource,
    private val songEntityQueries: SongEntityQueries,
) : SongLocalSource {

    private val io: CoroutineDispatcher
        get() = dispatcherProvider.io

    override fun getSongs(): Flow<List<Song>> =
        songEntityQueries
            .getSongsWithRelations()
            .asFlow()
            .mapToList(io)
            .map { entities ->
                entities.map(GetSongsWithRelations::toDomain)
            }

    override fun getSongsByAlbum(albumId: Long): Flow<List<Song>> =
        songEntityQueries
            .getSongsByAlbumWithRelations(albumId)
            .asFlow()
            .mapToList(io)
            .map { entities ->
                entities.map(GetSongsByAlbumWithRelations::toDomain)
            }

    override fun getSongsByArtist(artistId: Long): Flow<List<Song>> =
        songEntityQueries
            .getSongsByArtistWithRelations(artistId)
            .asFlow()
            .mapToList(io)
            .map { entities ->
                entities.map(GetSongsByArtistWithRelations::toDomain)
            }

    override fun getSongById(id: Long): Flow<Song?> =
        songEntityQueries
            .getSongByIdWithRelations(id)
            .asFlow()
            .mapToOneOrNull(io)
            .map { entity ->
                entity?.toDomain()
            }

    override suspend fun fetchSongs(): Result<Unit> = runCatching {

        val deviceSongs = localStorageSource.getSongs()

        val deviceIds = deviceSongs
            .asSequence()
            .map(SongEntity::id)
            .toSet()

        val dbSongs = songEntityQueries
            .getSongs()
            .executeAsList()

        val dbSongsById = dbSongs.associateBy(SongEntity::id)
        val removedIds = dbSongsById.keys - deviceIds
        val insertedSongs = mutableListOf<SongEntity>()

        songEntityQueries.transaction {
            removedIds.forEach(songEntityQueries::deleteSongById)
            deviceSongs.forEach { newSong ->
                val oldSong = dbSongsById[newSong.id]
                when {
                    oldSong == null -> {
                        songEntityQueries.insertSong(newSong)
                        insertedSongs += newSong
                    }

                    oldSong.lastModified != newSong.lastModified -> {
                        songEntityQueries.updateSong(
                            title = newSong.title,
                            duration = newSong.duration,
                            uri = newSong.uri,
                            albumId = newSong.albumId,
                            artistId = newSong.artistId,
                            lastModified = newSong.lastModified,
                            id = newSong.id,
                        )
                    }
                }
            }
        }

        syncArtwork(
            removedIds = removedIds,
            insertedSongs = insertedSongs,
        )
    }

    private suspend fun syncArtwork(
        removedIds: Set<Long>,
        insertedSongs: List<SongEntity>,
    ) = coroutineScope {

        val deleteJobs = removedIds.map { id ->
            async(io) {
                artworkCache.deleteArtworkFromCache(id)
            }
        }

        val cacheJobs = insertedSongs.map { song ->
            async(io) {
                val artwork = artworkCache
                    .getAndCacheArtwork(song.id)
                    ?: return@async
                songEntityQueries.insertArtwork(
                    id = song.id,
                    artwork = artwork,
                )
            }
        }
        (deleteJobs + cacheJobs).awaitAll()
    }
}