package ua.kucher.player.local.artist

import kotlinx.coroutines.flow.map
import ua.kucher.player.local.LocalStorageSource

internal class ArtistLocalSourceImpl(
    private val localStorageSource: LocalStorageSource,
    private val artistDao: ArtistDao
) : ArtistLocalSource {

    override fun getArtistById(id: Long) = artistDao.getArtistById(id).map { entity ->
        entity?.toDomain()
    }

    override fun getArtists() = artistDao.getArtists().map { artists ->
        artists.map(ArtistEntity::toDomain)
    }

    override suspend fun fetchArtists() = runCatching {
        val artistsInDevice = localStorageSource.getArtists()
        val deviceMap = artistsInDevice.associateBy { it.id }
        val dbArtists = artistDao.getArtistsSnapshot()
        val dbMap = dbArtists.associateBy { it.id }

        val toInsert = artistsInDevice.filter { it.id !in dbMap }
        val toUpdate = artistsInDevice.filter { device ->
            val db = dbMap[device.id] ?: return@filter false
            db.name != device.name ||
                    db.numberOfAlbums != device.numberOfAlbums ||
                    db.numberOfSongs != device.numberOfSongs
        }

        val toDelete = dbArtists.filter { it.id !in deviceMap }
        artistDao.mergeArtist(
            insert = toInsert,
            upsert = toUpdate,
            delete = toDelete
        )
    }
}