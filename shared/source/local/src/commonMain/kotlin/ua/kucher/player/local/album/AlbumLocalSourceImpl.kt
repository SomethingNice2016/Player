package ua.kucher.player.local.album

import app.cash.sqldelight.coroutines.asFlow
import kotlinx.coroutines.flow.map
import ua.kucher.player.database.AlbumEntityQueries
import ua.kucher.player.local.LocalStorageSource
import ua.kucher.player.local.mapToList

internal class AlbumLocalSourceImpl(
    private val localStorageSource: LocalStorageSource,
    private val albumEntityQueries: AlbumEntityQueries
) : AlbumLocalSource {
    override fun getAlbumById(id: Long) = albumEntityQueries
        .getAlbums()
        .asFlow().map { query ->
            query.executeAsOne().toDomain()
        }

    override fun getAlbums() = albumEntityQueries
        .getAlbums()
        .asFlow()
        .mapToList { entity ->
            entity.toDomain()
        }

    override fun getAlbumsByArtist(artistId: Long) = albumEntityQueries
        .getAlbumByArtist(artistId)
        .asFlow()
        .mapToList { entity ->
            entity.toDomain()
        }

    override suspend fun fetchAlbums() {
        val albumsInDevice = localStorageSource.getAlbums()
        albumEntityQueries.deleteAllAlbums()
        albumsInDevice.forEach { album ->
            albumEntityQueries.insertAlbum(album)
        }
    }
}