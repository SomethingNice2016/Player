package ua.kucher.player.theme.extensions

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import ua.kucher.player.core.common.utils.rememberNavigationBarHeight
import ua.kucher.player.theme.PlayerTheme

internal val bottomNavHeight: Dp
    @Composable
    get() = with(PlayerTheme.dimens) {
        val navigationBarPadding = rememberNavigationBarHeight()
        return dimens8Px * 2 + menuIconSize + navigationBarPadding
    }

internal val miniPlayerHeight: Dp
    @Composable
    get() = with(PlayerTheme.dimens) {
        dimens10Px + dimens12Px + songIconSize
    }


@Composable
internal fun Modifier.bottomNavPaddings() =
    padding(bottom = bottomNavHeight)

@Composable
internal fun Modifier.miniPlayerPaddings() =
    padding(bottom = miniPlayerHeight)

@Composable
internal fun BottomNavSpacer(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(bottomNavHeight))
}

@Composable
internal fun MiniPlayerSpacer(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(miniPlayerHeight))
}


internal fun Modifier.playerDragEvents(
    onTap: () -> Unit,
    onVerticalDrag: (delta: Float) -> Unit,
    onVerticalDagStart: (offset: Offset) -> Unit,
    onVerticalDagEnd: () -> Unit,
): Modifier {
    return pointerInput(Unit) {
        detectVerticalDragGestures(
            onVerticalDrag = { change, dragAmount ->
                change.consume()
                val delta = dragAmount / size.height
                onVerticalDrag(delta)
            },
            onDragStart = onVerticalDagStart,
            onDragEnd = onVerticalDagEnd
        )
    }.pointerInput(Unit) {
        detectTapGestures(
            onTap = { onTap() }
        )
    }
}
