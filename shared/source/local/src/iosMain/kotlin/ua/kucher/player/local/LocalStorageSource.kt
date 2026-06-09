package ua.kucher.player.local

import ua.kucher.player.database.AlbumEntity
import ua.kucher.player.database.ArtistEntity
import ua.kucher.player.database.SongEntity

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class LocalStorageSource {

    actual suspend fun getSongs(): List<SongEntity> {
        TODO("Not implemented")
    }

    actual suspend fun getAlbums(): List<AlbumEntity> {
        TODO("Not yet implemented")
    }

    actual suspend fun getArtists(): List<ArtistEntity> {
        TODO("Not yet implemented")
    }

}