package ua.kucher.player

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun openUrl(url: String) {
    NSURL.URLWithString(URLString = url)?.let { nsUrl ->
        UIApplication.sharedApplication.openURL(nsUrl)
    }
}