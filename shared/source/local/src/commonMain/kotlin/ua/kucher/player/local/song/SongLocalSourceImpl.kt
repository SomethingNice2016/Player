package ua.kucher.player.local.song

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ua.kucher.player.core.common.coroutines.dispather.DispatcherProvider
import ua.kucher.player.core.common.coroutines.mapNotNull
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
        songDao.getSongs().map { entities ->
            entities.map(SongDto::toDomain)
        }

    override fun getFavoriteSong(): Flow<List<Song>> =
        songDao.getFavoriteSongs().map { entities ->
            entities.map(SongDto::toDomain)
        }

    override fun getTopSongs(): Flow<List<Song>> =
        songDao.getTopSongs().map { entities ->
            entities.map(SongDto::toDomain)
        }

    override fun getRecentlyPlayedSongs(): Flow<List<Song>> =
        songDao.getRecentlyPlayedSongs().map { entities ->
            entities.map(SongDto::toDomain)
        }

    override fun getSongsByPlaylist(playlistId: Long): Flow<List<Song>> =
        songDao.getSongsByPlaylist(playlistId).map { entities ->
            entities.map(SongDto::toDomain)
        }

    override fun getSongsByAlbum(albumId: Long): Flow<List<Song>> =
        songDao.getSongsByAlbum(albumId).map { entities ->
            entities.map(SongDto::toDomain)
        }

    override fun getSongsByArtist(artistId: Long): Flow<List<Song>> =
        songDao.getSongsByArtist(artistId).map { entities ->
            entities.map(SongDto::toDomain)
        }

    override fun searchSongsByTitle(title: String): Flow<List<Song>> =
        songDao.searchSongsByTitle(title).map { entities ->
            entities.map(SongDto::toDomain)
        }

    override fun getSongById(id: Long): Flow<Song?> =
        songDao.getSongById(id).mapNotNull(SongDto::toDomain)

    override fun getSongsCount(): Flow<Int> =
        songDao.getSongsCount()

    override fun getFavoriteSongsCount(): Flow<Int> =
        songDao.getFavoriteSongsCount()

    override fun getSongsCountByPlaylist(playlistId: Long): Flow<Int> =
        songDao.getSongsCountByPlaylist(playlistId)

    override suspend fun updateFavoriteTimestamp(id: Long, timestamp: Long?) = runCatching {
        songDao.updateFavoriteTimestamp(id, timestamp)
    }

    override suspend fun registerPlayback(id: Long, timestamp: Long) = runCatching {
        songDao.registerPlayback(id, timestamp)
    }

    override suspend fun fetchSongs(): Result<Unit> = runCatching {
        val songsInDevice = localStorageSource.getSongs()
        val deviceMap = songsInDevice.associateBy { it.id }
        val dbSongs = songDao.getSongsSnapshot()
        val dbMap = dbSongs.associateBy { it.id }

        val toInsert = songsInDevice.filter { it.id !in dbMap }
        val toUpdate = songsInDevice.filter { device ->
            val db = dbMap[device.id] ?: return@filter false
            db.lastModified != device.lastModified
        }

        val toDelete = dbSongs.filter { it.id !in deviceMap }

        songDao.mergeSongs(
            upsert = toInsert + toUpdate,
            delete = toDelete
        )

        syncArtwork(
            removedSongs = toDelete,
            insertedSongs = toInsert,
        )
    }

    private suspend fun syncArtwork(
        removedSongs: List<SongEntity>,
        insertedSongs: List<SongEntity>,
    ) = coroutineScope {
        removedSongs.map { song ->
            launch {
                artworkCache.deleteSongArtworkFromCache(song.id)
            }
        }.joinAll()

        val songsWithArtworks = insertedSongs.map { song ->
            async(dispatcherProvider.artworkCache) {
                artworkCache.getAndCacheSongArtwork(song.id)?.let { artwork ->
                    SongWithArtwork(
                        songId = song.id,
                        artwork = artwork
                    )
                }
            }
        }.awaitAll().filterNotNull()
        songDao.setArtworks(songsWithArtworks)
    }
}