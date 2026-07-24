package ua.kucher.player.core.common.share

import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path
import platform.Foundation.NSURL
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UINavigationController
import platform.UIKit.UITabBarController
import platform.UIKit.UIViewController

@OptIn(ExperimentalForeignApi::class)
class IOSSharingManager : SharingManager {

    override fun shareUri(uri: String) {
        presentShareSheet(
            listOf(NSURL.URLWithString(uri) ?: return)
        )
    }

    override fun shareFile(path: Path) {
        val url = NSURL.fileURLWithPath(path.toString())
        presentShareSheet(listOf(url))
    }

    private fun presentShareSheet(items: List<Any>) {
        val controller = UIActivityViewController(
            activityItems = items,
            applicationActivities = null
        )

        topViewController()?.presentViewController(
            controller,
            animated = true,
            completion = null
        )
    }

    private fun topViewController(): UIViewController? {
        var controller =
            UIApplication.sharedApplication.keyWindow?.rootViewController

        while (true) {
            controller = when (controller) {
                is UINavigationController -> controller.visibleViewController
                is UITabBarController -> controller.selectedViewController
                else -> controller?.presentedViewController ?: break
            }
        }

        return controller
    }
}