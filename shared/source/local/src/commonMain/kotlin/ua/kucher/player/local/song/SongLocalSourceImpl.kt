package ua.kucher.player.local.song

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.kucher.player.core.ui.coroutines.dispather.DispatcherProvider
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

    override fun getTopSongs(): Flow<List<Song>> =
        songDao.getTopSongs().map { entities ->
            entities.map(SongDto::toDomain)
        }

    override fun getRecentlyPlayedSongs(): Flow<List<Song>> =
        songDao.getRecentlyPlayedSongs().map { entities ->
            entities.map(SongDto::toDomain)
        }

    override fun getSongsByPlaylist(playlistId: Long) =
        songDao.getSongsByPlaylist(playlistId).map { entities ->
            entities.map(SongDto::toDomain)
        }

    override fun getSongsByAlbum(albumId: Long) =
        songDao.getSongsByAlbum(albumId).map { entities ->
            entities.map(SongDto::toDomain)
        }

    override fun getSongsByArtist(artistId: Long) =
        songDao.getSongsByArtist(artistId).map { entities ->
            entities.map(SongDto::toDomain)
        }

    override fun searchSongsByTitle(title: String) =
        songDao.searchSongsByTitle(title).map { entities ->
            entities.map(SongDto::toDomain)
        }

    override fun getSongsCount() =
        songDao.getSongsCount()

    override fun getSongsCountByPlaylist(playlistId: Long) =
        songDao.getSongsCountByPlaylist(playlistId)

    override fun getSongById(id: Long) =
        songDao.getSongById(id).map { entity ->
            entity?.toDomain()
        }

    override suspend fun getListenCountById(id: Long) = runCatching {
        songDao.getListenCount(id)
    }

    override suspend fun updateListenCountById(id: Long, count: Int) = runCatching {
        songDao.updateListenCount(id, count)
    }

    override suspend fun updatePlayedTimeById(id: Long, timestamp: Long) = runCatching {
        songDao.updatePlayedTime(id, timestamp)
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
            insert = toInsert,
            upsert = toUpdate,
            delete = toDelete.map { it }
        )

        syncArtwork(
            removedIds = toDelete.map { it.id },
            insertedSongs = toInsert,
        )
    }

    private suspend fun syncArtwork(
        removedIds: List<Long>,
        insertedSongs: List<SongEntity>,
    ) = coroutineScope {
        removedIds.map { id ->
            async {
                artworkCache.deleteSongArtworkFromCache(id)
            }
        }.awaitAll()

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