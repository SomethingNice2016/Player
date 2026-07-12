package ua.kucher.player.local

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class ArtworkCache {

    suspend fun getAndCacheSongArtwork(songId: Long): String?

    suspend fun deleteSongArtworkFromCache(songId: Long)

}