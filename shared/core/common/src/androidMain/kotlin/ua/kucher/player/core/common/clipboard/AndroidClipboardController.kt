package ua.kucher.player.core.common.clipboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class AndroidClipboardController(
    private val context: Context
) : ClipboardController {

    private val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    override fun setText(text: String) {
        val clip = ClipData.newPlainText(context.applicationInfo.name, text)
        clipboardManager.setPrimaryClip(clip)
    }

}