package ua.kucher.player.local

import ua.kucher.player.database.AlbumEntity
import ua.kucher.player.database.ArtistEntity
import ua.kucher.player.database.SongEntity

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class LocalStorageSource {

    suspend fun getSongs(): List<SongEntity>

    suspend fun getArtists(): List<ArtistEntity>

    suspend fun getAlbums(): List<AlbumEntity>

}