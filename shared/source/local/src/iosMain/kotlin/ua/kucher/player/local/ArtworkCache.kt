package ua.kucher.player.local

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class ArtworkCache {

    actual suspend fun getAndCashArtwork(songId: Long, albumId: Long): Result<String> {
        TODO("Not yet implemented")
    }
}