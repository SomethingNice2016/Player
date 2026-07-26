package ua.kucher.player.core.common.bitmap

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import androidx.core.net.toUri
import kotlinx.coroutines.withContext
import ua.kucher.player.core.common.coroutines.dispather.DispatcherProvider

class AndroidBitmapLoader(
    private val context: Context,
    private val dispatcherProvider: DispatcherProvider
) : BitmapLoader {
    override suspend fun loadBitmap(uri: String): SharedBitmap? =
        withContext(dispatcherProvider.io) {
            runCatching {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val source = ImageDecoder.createSource(
                        context.contentResolver,
                        uri.toUri()
                    )
                    SharedBitmap(ImageDecoder.decodeBitmap(source))
                } else {
                    context.contentResolver.openInputStream(uri.toUri())
                        ?.use(BitmapFactory::decodeStream)?.let { nativeBitmap ->
                            SharedBitmap(nativeBitmap)
                        }
                }
            }.getOrNull()
        }

}