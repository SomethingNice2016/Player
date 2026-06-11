package ua.kucher.player.local

import android.content.ContentUris
import android.content.Context
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import java.io.File

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class ArtworkCache(private val context: Context) {

    companion object {
        private const val SONG_ARTWORK_DIR = "artwork/songs"
        private const val ARTWORK_FORMAT = ".jpg"
    }

    actual suspend fun getAndCacheArtwork(songId: Long) = runCatching {
        val artworkFile = File(
            context.filesDir,
            "$SONG_ARTWORK_DIR/$songId$ARTWORK_FORMAT"
        )

        if (artworkFile.exists()) {
            return@runCatching artworkFile.toURI().toString()
        }

        val uri = ContentUris.withAppendedId(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            songId
        )

        val retriever = MediaMetadataRetriever()

        retriever.setDataSource(
            context,
            uri
        )

        val bytes = retriever.embeddedPicture
        retriever.release()

        if (bytes?.toList().isNullOrEmpty()) {
            return@runCatching null
        }

        artworkFile.parentFile?.mkdirs()

        artworkFile.outputStream().use { stream ->
            stream.write(bytes)
        }
        return@runCatching artworkFile.toURI().toString()
    }.getOrNull()

    actual suspend fun deleteArtworkFromCache(songId: Long) {
        runCatching {
            val artworkFile = File(
                context.filesDir,
                "$SONG_ARTWORK_DIR/$songId$ARTWORK_FORMAT"
            )

            if (!artworkFile.exists()) return@runCatching

            artworkFile.delete()
        }
    }

}