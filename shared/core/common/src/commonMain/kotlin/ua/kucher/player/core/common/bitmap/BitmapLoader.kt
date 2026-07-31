package ua.kucher.player.core.common.bitmap

interface BitmapLoader {

    suspend fun loadBitmap(uri: String): SharedBitmap?

}