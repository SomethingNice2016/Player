package ua.kucher.player.local.album

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.kucher.player.core.common.coroutines.mapNotNull
import ua.kucher.player.entity.Album
import ua.kucher.player.local.LocalStorageSource
import ua.kucher.player.local.album.entity.AlbumDto
import ua.kucher.player.local.album.entity.AlbumEntity
import ua.kucher.player.local.album.entity.toDomain

internal class AlbumLocalSourceImpl(
    private val localStorageSource: LocalStorageSource,
    private val albumDao: AlbumDao
) : AlbumLocalSource {

    override fun getAlbumById(id: Long): Flow<Album?> =
        albumDao.getAlbumById(id).mapNotNull(AlbumDto::toDomain)

    override fun getAlbums(): Flow<List<Album>> =
        albumDao.getAlbums().map { entities ->
            entities.map(AlbumDto::toDomain)
        }

    override fun getAlbumsByArtist(artistId: Long): Flow<List<Album>> =
        albumDao.getAlbumsByArtist(artistId).map { entities ->
            entities.map(AlbumDto::toDomain)
        }

    override fun searchAlbumsByTitle(title: String): Flow<List<Album>> =
        albumDao.searchAlbumsByTitle(title).map { entities ->
            entities.map(AlbumDto::toDomain)
        }

    override fun getAlbumsCount(): Flow<Int> =
        albumDao.getAlbumsCount()

    override suspend fun fetchAlbums() = runCatching {
        val albumsInDevice = localStorageSource.getAlbums()
        val dbAlbums = albumDao.getAlbumsSnapshot()

        val deviceMap = albumsInDevice.associateBy { it.id }
        val dbMap = dbAlbums.associateBy { it.id }

        val toInsert = mutableListOf<AlbumEntity>()
        val toUpdate = mutableListOf<AlbumEntity>()
        val toDelete = mutableListOf<AlbumEntity>()

        albumsInDevice.forEach { device ->
            val db = dbMap[device.id]
            if (db == null) {
                toInsert += device
            } else if (
                db.title != device.title ||
                db.artistId != device.artistId ||
                db.artwork != device.artwork
            ) {
                toUpdate += device
            }
        }

        dbAlbums.forEach { db ->
            if (db.id !in deviceMap) {
                toDelete += db
            }
        }
        albumDao.merge(
            upsert = toUpdate + toInsert,
            deleteIds = toDelete
        )
    }
}