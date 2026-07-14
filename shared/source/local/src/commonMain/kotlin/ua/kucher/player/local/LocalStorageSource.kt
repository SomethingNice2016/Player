package ua.kucher.player.local

import ua.kucher.player.local.album.entity.AlbumEntity
import ua.kucher.player.local.artist.ArtistEntity
import ua.kucher.player.local.song.entity.SongEntity

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class LocalStorageSource {

    suspend fun getSongs(): List<SongEntity>

    suspend fun getArtists(): List<ArtistEntity>

    suspend fun getAlbums(): List<AlbumEntity>

}