package ua.kucher.player.core.common.clipboard

import platform.UIKit.UIPasteboard

class IOSClipboardController : ClipboardController {

    private val pasteboard: UIPasteboard
        get() = UIPasteboard.generalPasteboard

    override fun setText(text: String) {
        pasteboard.string = text
    }

}