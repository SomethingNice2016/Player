package ua.kucher.player.local.artist

import app.cash.sqldelight.coroutines.asFlow
import ua.kucher.player.database.ArtisEntityQueries
import ua.kucher.player.database.ArtistEntity
import ua.kucher.player.local.LocalStorageSource
import ua.kucher.player.local.mapToList
import ua.kucher.player.local.mapToOne

internal class ArtistLocalSourceImpl(
    private val localStorageSource: LocalStorageSource,
    private val artistEntityQueries: ArtisEntityQueries
) : ArtistLocalSource {

    override fun getArtistById(id: Long) =
        artistEntityQueries
            .getArtistById(id)
            .asFlow()
            .mapToOne(ArtistEntity::toDomain)

    override fun getArtists() =
        artistEntityQueries
            .getArtists()
            .asFlow()
            .mapToList(ArtistEntity::toDomain)

    override suspend fun fetchArtists() = runCatching {
        val artistsInDevice = localStorageSource.getArtists()
        val deviceMap = artistsInDevice.associateBy { it.id }
        val dbArtists = artistEntityQueries.getArtists().executeAsList()
        val dbMap = dbArtists.associateBy { it.id }

        val toInsert = artistsInDevice.filter { it.id !in dbMap }
        val toUpdate = artistsInDevice.filter { device ->
            val db = dbMap[device.id] ?: return@filter false
            db.name != device.name ||
                    db.numberOfAlbums != device.numberOfAlbums ||
                    db.numberOfSongs != device.numberOfSongs
        }

        val toDelete = dbArtists.filter { it.id !in deviceMap }

        artistEntityQueries.transaction {
            toInsert.forEach(artistEntityQueries::insertArtist)
            toUpdate.forEach(artistEntityQueries::insertArtist) // upsert
            toDelete.forEach { artistEntityQueries.deleteArtist(it.id) }
        }
    }
}