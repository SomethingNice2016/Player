@file:OptIn(BetaInteropApi::class)

package ua.kucher.player.core.common.bitmap

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.useContents
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.create
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@OptIn(ExperimentalForeignApi::class)
actual class SharedBitmap(val nativeImage: UIImage) {
    actual val width: Int = nativeImage.size.useContents { width.toInt() }
    actual val height: Int = nativeImage.size.useContents { height.toInt() }

    actual fun getPixelColor(x: Int, y: Int): Int = 0 // Реалізація через CGContext

    actual fun readPixelsToBuffer(): ByteArray {
        val nsData = UIImagePNGRepresentation(nativeImage) ?: return byteArrayOf()
        val bytes = ByteArray(nsData.length.toInt())
        if (bytes.isNotEmpty()) bytes.usePinned { pinned ->
            platform.posix.memcpy(pinned.addressOf(0), nsData.bytes, nsData.length)
        }
        return bytes
    }

    actual fun toByteArray(quality: Int) = readPixelsToBuffer()

    actual override fun equals(other: Any?) = nativeImage == other

    actual override fun hashCode() = nativeImage.hashCode()

    actual companion object {
        actual fun fromByteArray(bytes: ByteArray): SharedBitmap {
            val nsData = bytes.usePinned { pinned ->
                NSData.create(bytes = pinned.addressOf(0), length = bytes.size.toULong())
            }
            return SharedBitmap(
                UIImage.imageWithData(nsData) ?: throw IllegalArgumentException("Failed to decode Image")
            )
        }
    }
}