package ua.kucher.player.local.album

import app.cash.sqldelight.coroutines.asFlow
import ua.kucher.player.database.AlbumEntity
import ua.kucher.player.database.AlbumEntityQueries
import ua.kucher.player.local.LocalStorageSource
import ua.kucher.player.local.mapToList
import ua.kucher.player.local.mapToOne

internal class AlbumLocalSourceImpl(
    private val localStorageSource: LocalStorageSource,
    private val albumEntityQueries: AlbumEntityQueries
) : AlbumLocalSource {

    override fun getAlbumById(id: Long) =
        albumEntityQueries
            .getAlbumById(id)
            .asFlow()
            .mapToOne(AlbumEntity::toDomain)

    override fun getAlbums() =
        albumEntityQueries
            .getAlbums()
            .asFlow()
            .mapToList(AlbumEntity::toDomain)

    override fun getAlbumsByArtist(artistId: Long) =
        albumEntityQueries
            .getAlbumByArtist(artistId)
            .asFlow()
            .mapToList(AlbumEntity::toDomain)

    override suspend fun fetchAlbums() = runCatching {
        val albumsInDevice = localStorageSource.getAlbums()
        val dbAlbums = albumEntityQueries.getAlbums().executeAsList()

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

        albumEntityQueries.transaction {
            toInsert.forEach(albumEntityQueries::insertAlbum)
            toUpdate.forEach(albumEntityQueries::insertAlbum) // upsert
            toDelete.forEach(albumEntityQueries::deleteAlbum)
        }
    }
}