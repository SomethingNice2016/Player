package ua.kucher.player.theme.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

private const val IMAGE_REQUEST_FIXED_THREADS = 8

@Composable
internal fun rememberImageRequest(
    uri: String?,
    height: Int,
    width: Int,
    dispatcher: CoroutineDispatcher? = Dispatchers.IO.limitedParallelism(IMAGE_REQUEST_FIXED_THREADS)
): ImageRequest {

    val context = LocalPlatformContext.current

    return remember(context, uri, width, height) {
        val builder = ImageRequest.Builder(context)
            .data(uri)
            .memoryCacheKey(uri)
            .size(
                width = width,
                height = height
            )

        if (dispatcher != null) {
            builder.coroutineContext(dispatcher)
        }

        builder.build()
    }
}

@Composable
internal fun rememberImageRequest(
    uri: String?,
) = rememberAsyncImagePainter(
    model = uri,
)