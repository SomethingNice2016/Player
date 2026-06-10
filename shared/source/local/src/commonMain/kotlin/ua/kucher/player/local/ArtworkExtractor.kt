package ua.kucher.player.local

import ua.kucher.player.core.common.bitmap.SharedBitmap

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class ArtworkExtractor {

    suspend fun extractSongArtwork(songId: Long): SharedBitmap?

}