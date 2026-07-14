package ua.kucher.player.local.album

import kotlinx.coroutines.flow.map
import ua.kucher.player.local.LocalStorageSource
import ua.kucher.player.local.album.entity.AlbumEntity
import ua.kucher.player.local.album.entity.toDomain

internal class AlbumLocalSourceImpl(
    private val localStorageSource: LocalStorageSource,
    private val albumDao: AlbumDao
) : AlbumLocalSource {

    override fun getAlbumById(id: Long) =
        albumDao.getAlbumById(id).map { entity ->
            entity?.toDomain()
        }

    override fun getAlbums() =
        albumDao.getAlbums().map { entities ->
            entities.map { entity ->
                entity.toDomain()
            }
        }

    override fun getAlbumsByArtist(artistId: Long) =
        albumDao.getAlbumsByArtist(artistId).map { entities ->
            entities.map { entity ->
                entity.toDomain()
            }
        }

    override fun searchAlbumsByTitle(title: String) =
        albumDao.searchAlbumsByTitle(title).map { entities ->
            entities.map { entity ->
                entity.toDomain()
            }
        }

    override fun getAlbumsCount() =
        albumDao.getAlbumsCount()

    override suspend fun fetchAlbums() = runCatching {
        val albumsInDevice = localStorageSource.getAlbums()
        val dbAlbums = albumDao.getAlbumsSnapshot()

        val deviceMap = albumsInDevice.associateBy { it.id }
        val dbMap = dbAlbums.associateBy { it.id }

        val toInsert = mutableListOf<AlbumEntity>()
        val toUpdate = mutableListOf<AlbumEntity>()
        val toDelete = mutableListOf<Long>()

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
                toDelete += db.id
            }
        }
        albumDao.mergeAlbum(
            insert = toInsert,
            upsert = toUpdate,
            deleteIds = toDelete
        )
    }
}