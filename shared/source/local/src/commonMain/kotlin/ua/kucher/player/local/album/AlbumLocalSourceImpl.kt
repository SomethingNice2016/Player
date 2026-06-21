package ua.kucher.player.local.album

import app.cash.sqldelight.coroutines.asFlow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ua.kucher.player.database.AlbumEntityQueries
import ua.kucher.player.local.LocalStorageSource
import ua.kucher.player.local.mapToList
import ua.kucher.player.local.mapToOne

internal class AlbumLocalSourceImpl(
    private val localStorageSource: LocalStorageSource,
    private val albumEntityQueries: AlbumEntityQueries
) : AlbumLocalSource {
    override fun getAlbumById(id: Long) = albumEntityQueries
        .getAlbums()
        .asFlow()
        .mapToOne { entity ->
            entity.toDomain()
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

    override suspend fun fetchAlbums() = runCatching {
        val albumsInDevice = localStorageSource.getAlbums()
        albumEntityQueries.deleteAllAlbums()
        coroutineScope {
            albumsInDevice.map { album ->
                launch { albumEntityQueries.insertAlbum(album) }
            }
        }.joinAll()
    }
}