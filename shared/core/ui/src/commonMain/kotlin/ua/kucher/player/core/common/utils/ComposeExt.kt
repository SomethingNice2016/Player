package ua.kucher.player.core.common.utils

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

fun Modifier.pulseAnimation() = composed {
    val alpha = rememberPulseAnimation()
    alpha(alpha)
}


val ScrollableState.canScroll: Boolean
    get() = canScrollBackward || canScrollForward


@Composable
fun LeftLayoutDirection(block: @Composable () -> Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        block()
    }
}

@Composable
fun RightLayoutDirection(block: @Composable () -> Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        block()
    }
}