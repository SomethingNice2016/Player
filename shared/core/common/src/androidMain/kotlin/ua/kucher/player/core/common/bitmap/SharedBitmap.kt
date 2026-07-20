package ua.kucher.player.core.common.bitmap

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.graphics.get
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SharedBitmap(val nativeBitmap: Bitmap) {
    actual val width: Int = nativeBitmap.width
    actual val height: Int = nativeBitmap.height

    actual fun getPixelColor(x: Int, y: Int): Int = nativeBitmap[x, y]

    actual fun readPixelsToBuffer(): ByteArray {
        val buffer = ByteBuffer.allocate(nativeBitmap.byteCount)
        nativeBitmap.copyPixelsToBuffer(buffer)
        return buffer.array()
    }

    actual fun toByteArray(quality: Int): ByteArray {
        val stream = ByteArrayOutputStream()
        nativeBitmap.compress(Bitmap.CompressFormat.PNG, quality, stream)
        return stream.toByteArray()
    }

    actual override fun equals(other: Any?): Boolean {
        return nativeBitmap == other
    }

    actual override fun hashCode(): Int = nativeBitmap.hashCode()

    actual companion object {
        actual fun fromByteArray(bytes: ByteArray): SharedBitmap =
            SharedBitmap(
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    ?: throw IllegalArgumentException("Failed to decode Bitmap")
            )
    }
}