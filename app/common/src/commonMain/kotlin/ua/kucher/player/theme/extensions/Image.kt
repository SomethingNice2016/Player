package ua.kucher.player.theme.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

private const val IMAGE_REQUEST_FIXED_THREADS = 16

private val ImageDispatcher = Dispatchers.IO.limitedParallelism(IMAGE_REQUEST_FIXED_THREADS)

@Composable
internal fun rememberImageRequest(
    model: Any?,
    height: Int,
    width: Int,
    dispatcher: CoroutineDispatcher? = ImageDispatcher
): ImageRequest {

    val context = LocalPlatformContext.current

    return remember(model, height, width) {
        val builder = ImageRequest.Builder(context)
            .data(model)
            .memoryCacheKey(model?.toString())
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
    model: Any?,
) = rememberAsyncImagePainter(
    model = model,
)