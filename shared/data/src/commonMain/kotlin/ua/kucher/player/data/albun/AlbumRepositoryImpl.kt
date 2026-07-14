package ua.kucher.player.data.albun

import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ua.kucher.player.core.common.coroutines.dispather.DispatcherProvider
import ua.kucher.player.local.album.AlbumLocalSource


internal class AlbumRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val albumLocalSource: AlbumLocalSource
) : AlbumRepository {

    override fun getAlbums() = albumLocalSource.getAlbums()
        .flowOn(dispatcherProvider.io)

    override fun searchAlbumsByTitle(title: String) = albumLocalSource.searchAlbumsByTitle(title)
        .flowOn(dispatcherProvider.io)

    override fun getAlbumById(id: Long) = albumLocalSource.getAlbumById(id)
        .flowOn(dispatcherProvider.io)

    override fun getAlbumsCount() = albumLocalSource.getAlbumsCount()
        .flowOn(dispatcherProvider.io)

    override suspend fun fetchAlbums() = withContext(dispatcherProvider.io) {
        albumLocalSource.fetchAlbums()
    }

}