package ua.kucher.player.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIScreen

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberScreenSizeHeight(): Dp {
    return UIScreen.mainScreen.bounds.useContents {
        size.height.dp
    }
}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberScreenSizeWidth(): Dp {
    return UIScreen.mainScreen.bounds.useContents {
        size.width.dp
    }
}