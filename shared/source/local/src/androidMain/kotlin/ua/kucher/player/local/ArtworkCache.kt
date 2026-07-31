package ua.kucher.player.local

import android.content.ContentUris
import android.content.Context
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import ua.kucher.player.core.common.bitmap.cropToSquare
import ua.kucher.player.core.common.bitmap.toBitmap
import ua.kucher.player.core.common.bitmap.toByteArray
import ua.kucher.player.core.common.security.sha256
import java.io.File

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class ArtworkCache(private val context: Context) {

    companion object {
        private const val SONG_ARTWORK_DIR = "artwork/songs"
        private const val ARTWORK_FORMAT = ".webp"
    }

    actual suspend fun getAndCacheSongArtwork(songId: Long): String? = runCatching {

        val uri = ContentUris.withAppendedId(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            songId
        )

        val retriever = MediaMetadataRetriever()

        retriever.setDataSource(context, uri)

        val bytes = retriever.embeddedPicture
            ?.toBitmap()
            ?.cropToSquare()
            ?.toByteArray()

        retriever.release()

        if (bytes == null) {
            return@runCatching null
        }

        if (bytes.isEmpty()) {
            return@runCatching null
        }

        val hash = sha256(bytes)

        val artworkFile = File(
            context.filesDir,
            "$SONG_ARTWORK_DIR/$hash$ARTWORK_FORMAT"
        )

        if (!artworkFile.exists()) {
            artworkFile.parentFile?.mkdirs()

            artworkFile.outputStream().use {
                it.write(bytes)
            }
        }

        artworkFile.toURI().toString()
    }.getOrNull()

    actual suspend fun deleteSongArtworkFromCache(songId: Long) {
        //TODO
    }
}