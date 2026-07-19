package ua.kucher.player.local.artist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.kucher.player.core.common.coroutines.mapNotNull
import ua.kucher.player.entity.Artist
import ua.kucher.player.local.LocalStorageSource

internal class ArtistLocalSourceImpl(
    private val localStorageSource: LocalStorageSource,
    private val artistDao: ArtistDao
) : ArtistLocalSource {

    override fun getArtistById(id: Long): Flow<Artist?> =
        artistDao.getArtistById(id).mapNotNull(ArtistEntity::toDomain)

    override fun getArtists(): Flow<List<Artist>> =
        artistDao.getArtists().map { artists ->
            artists.map(ArtistEntity::toDomain)
        }

    override fun getTopArtists(): Flow<List<Artist>> =
        artistDao.getTopArtists().map { artists ->
            artists.map(ArtistEntity::toDomain)
        }

    override fun searchArtistsByName(name: String): Flow<List<Artist>> =
        artistDao.searchArtistByName(name).map { artists ->
            artists.map(ArtistEntity::toDomain)
        }

    override fun getArtistsCount(): Flow<Int> =
        artistDao.getArtistsCount()

    override suspend fun getListenCountById(id: Long) = runCatching {
        artistDao.getListenCount(id)
    }

    override suspend fun updateListenCountById(id: Long, count: Int) = runCatching {
        artistDao.updateListenCount(id, count)
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
        artistDao.merge(
            upsert = toUpdate + toInsert,
            delete = toDelete
        )
    }
}