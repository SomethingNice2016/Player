package ua.kucher.player.local

import android.content.ContentUris
import android.content.Context
import android.media.MediaMetadataRetriever
import android.provider.MediaStore

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class ArtworkExtractor(private val context: Context) {

    actual suspend fun extractSongArtwork(songId: Long): ByteArray? {
        val uri = ContentUris.withAppendedId(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            songId
        )

        return try {
            val retriever = MediaMetadataRetriever()

            retriever.setDataSource(
                context,
                uri
            )
            retriever.embeddedPicture.also {
                retriever.release()
            }
        } catch (_: Exception) {
            null
        }
    }
}