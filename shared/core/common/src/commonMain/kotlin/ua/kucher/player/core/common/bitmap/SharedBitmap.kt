package ua.kucher.player.core.common.bitmap

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class SharedBitmap {
    val width: Int
    val height: Int

    fun getPixelColor(x: Int, y: Int): Int

    fun readPixelsToBuffer(): ByteArray

    fun toByteArray(quality: Int = 100): ByteArray

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int

    companion object {
        fun fromByteArray(bytes: ByteArray): ua.kucher.player.core.common.bitmap.SharedBitmap
    }
}