package ua.kucher.player.core.common.bitmap

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

fun ByteArray.toBitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}

fun Bitmap.cropToSquare(): Bitmap {
    val size = minOf(width, height)
    val x = (width - size) / 2
    val y = (height - size) / 2
    return Bitmap.createBitmap(this, x, y, size, size)
}

fun Bitmap.toByteArray(format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(format, 90, stream)
    return stream.toByteArray()
}