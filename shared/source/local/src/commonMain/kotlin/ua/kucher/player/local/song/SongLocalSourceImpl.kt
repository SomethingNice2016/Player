package ua.kucher.player.local.song

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.kucher.player.core.common.coroutines.dispather.DispatcherProvider
import ua.kucher.player.entity.Song
import ua.kucher.player.local.ArtworkCache
import ua.kucher.player.local.LocalStorageSource
import ua.kucher.player.local.song.entity.SongDto
import ua.kucher.player.local.song.entity.SongEntity
import ua.kucher.player.local.song.entity.SongWithArtwork
import ua.kucher.player.local.song.entity.toDomain


@OptIn(ExperimentalCoroutinesApi::class)
internal class SongLocalSourceImpl(
    private val artworkCache: ArtworkCache,
    private val localStorageSource: LocalStorageSource,
    private val dispatcherProvider: DispatcherProvider,
    private val songDao: SongDao,
) : SongLocalSource {

    override fun getSongs(): Flow<List<Song>> =
        songDao.getSongs()
            .map { entities ->
                entities.map(SongDto::toDomain)
            }

    override fun getSongsByAlbum(albumId: Long): Flow<List<Song>> =
        songDao.getSongsByAlbum(albumId)
            .map { entities ->
                entities.map(SongDto::toDomain)
            }

    override fun getSongsByArtist(artistId: Long): Flow<List<Song>> =
        songDao.getSongsByArtist(artistId)
            .map { entities ->
                entities.map(SongDto::toDomain)
            }

    override fun getSongById(id: Long): Flow<Song?> =
        songDao.getSongById(id)
            .map { entity ->
                entity?.toDomain()
            }

    override suspend fun fetchSongs(): Result<Unit> = runCatching {
        val result = songDao.mergeSongs(localStorageSource.getSongs())
        syncArtwork(
            removedIds = result.removedSongIds,
            insertedSongs = result.insertedSongs,
        )
    }

    private suspend fun syncArtwork(
        removedIds: Set<Long>,
        insertedSongs: List<SongEntity>,
    ) = coroutineScope {
        removedIds.map { id ->
            async {
                artworkCache.deleteArtworkFromCache(id)
            }
        }.awaitAll()

        val songsWithArtworks = insertedSongs.map { song ->
            async(dispatcherProvider.artworkCache) {
                artworkCache.getAndCacheArtwork(song.id)?.let { artwork ->
                    SongWithArtwork(
                        songId = song.id,
                        artwork = artwork
                    )
                }
            }
        }.awaitAll().filterNotNull()
        songDao.insertArtworks(songsWithArtworks)
    }
}