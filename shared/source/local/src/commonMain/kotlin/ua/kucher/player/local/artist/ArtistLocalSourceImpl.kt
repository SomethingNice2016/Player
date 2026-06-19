package ua.kucher.player.local.artist

import app.cash.sqldelight.coroutines.asFlow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ua.kucher.player.database.ArtisEntityQueries
import ua.kucher.player.local.LocalStorageSource
import ua.kucher.player.local.mapToList
import ua.kucher.player.local.mapToOne

internal class ArtistLocalSourceImpl(
    private val localStorageSource: LocalStorageSource,
    private val artistEntityQueries: ArtisEntityQueries
) : ArtistLocalSource {

    override fun getArtistById(id: Long) = artistEntityQueries
        .getArtistById(id)
        .asFlow()
        .mapToOne { entity ->
            entity.toDomain()
        }

    override fun getArtists() = artistEntityQueries
        .getArtists()
        .asFlow()
        .mapToList { entity ->
            entity.toDomain()
        }

    override suspend fun fetchArtists() = runCatching {
        val artistsInDevice = localStorageSource.getArtists()
        artistEntityQueries.deleteAllArtists()
        artistsInDevice.map { artist ->
            coroutineScope {
                launch { artistEntityQueries.insertArtist(artist) }
            }
        }.joinAll()
    }
}