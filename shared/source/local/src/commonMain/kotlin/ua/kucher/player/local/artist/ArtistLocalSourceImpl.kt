package ua.kucher.player.local.artist

import app.cash.sqldelight.coroutines.asFlow
import kotlinx.coroutines.flow.map
import ua.kucher.player.database.ArtisEntityQueries
import ua.kucher.player.local.LocalStorageSource
import ua.kucher.player.local.mapToList

internal class ArtistLocalSourceImpl(
    private val localStorageSource: LocalStorageSource,
    private val artistEntityQueries: ArtisEntityQueries
) : ArtistLocalSource {

    override fun getArtistById(id: Long) = artistEntityQueries
        .getArtistById(id)
        .asFlow().map { query ->
            query.executeAsOne().toDomain()
        }

    override fun getArtists() = artistEntityQueries
        .getArtists()
        .asFlow()
        .mapToList { entity ->
            entity.toDomain()
        }

    override suspend fun fetchArtists() {
        val artistsInDevice = localStorageSource.getArtists()
        artistEntityQueries.deleteAllArtists()
        artistsInDevice.forEach { artist ->
            artistEntityQueries.insertArtist(artist)
        }
    }
}