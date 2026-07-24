package ua.kucher.player.core.common.share

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import okio.Path

class AndroidSharingManager(
    private val context: Context
) : SharingManager {

    companion object {
        private const val AUDIO_TYPE = "audio/*"
        private const val TITLE = "Share song"
    }

    override fun shareUri(uri: String) {
        val intent = Intent.createChooser(
            Intent(Intent.ACTION_SEND).apply {
                type = AUDIO_TYPE
                putExtra(Intent.EXTRA_STREAM, uri.toUri())
            }, TITLE
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(intent)
    }

    override fun shareFile(path: Path) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            path.toFile()
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = AUDIO_TYPE
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(Intent.createChooser(intent, TITLE))
    }
}