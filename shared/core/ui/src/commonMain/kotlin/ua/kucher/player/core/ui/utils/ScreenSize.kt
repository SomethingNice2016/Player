package ua.kucher.player.core.ui.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp


@Composable
expect fun rememberScreenSizeHeight(): Dp

@Composable
expect fun rememberScreenSizeWidth(): Dp

@Composable
fun rememberStatusBarHeight(): Dp {
    val density = LocalDensity.current
    return with(density) {
        WindowInsets.statusBars
            .getTop(density)
            .toDp()
    }
}

@Composable
fun rememberNavigationBarHeight(): Dp {
    val density = LocalDensity.current
    return with(density) {
        WindowInsets.navigationBars
            .getBottom(this)
            .toDp()
    }
}