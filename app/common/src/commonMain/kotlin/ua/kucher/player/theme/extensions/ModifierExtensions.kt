package ua.kucher.player.theme.extensions

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ua.kucher.player.theme.PlayerTheme


private val bottomNavHeight: Dp
    @Composable
    get() = PlayerTheme.dimens.dimens8Px * 2 + PlayerTheme.dimens.menuIconSize + PlayerTheme.dimens.dimens24Px


@Composable
internal fun Modifier.bottomNavPaddings() =
    padding(bottom = bottomNavHeight)

@Composable
internal fun BottomNavSpacer() {
    Spacer(modifier = Modifier.height(bottomNavHeight))
}

internal fun Modifier.light(
    color: Color,
    radius: Dp
) = drawBehind {
    drawRoundRect(
        color = color.copy(alpha = 0.15f),
        cornerRadius = CornerRadius(24f)
    )
}.blur(20.dp)
