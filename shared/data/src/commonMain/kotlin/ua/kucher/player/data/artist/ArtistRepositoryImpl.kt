package ua.kucher.player.data.artist

import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ua.kucher.player.core.common.coroutines.dispather.DispatcherProvider
import ua.kucher.player.local.artist.ArtistLocalSource

internal class ArtistRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val artistLocalSource: ArtistLocalSource
) : ArtistRepository {

    override fun getArtistById(id: Long) = artistLocalSource.getArtistById(id)
        .flowOn(dispatcherProvider.io)

    override fun getArtistsCount() = artistLocalSource.getArtistsCount()
        .flowOn(dispatcherProvider.io)

    override fun getArtists() = artistLocalSource.getArtists()
        .flowOn(dispatcherProvider.io)

    override suspend fun fetchArtists() = withContext(dispatcherProvider.io) {
        artistLocalSource.fetchArtists()
    }

}