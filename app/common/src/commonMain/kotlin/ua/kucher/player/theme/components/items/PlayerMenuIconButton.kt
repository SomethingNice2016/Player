package ua.kucher.player.theme.components.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import ua.kucher.player.theme.PlayerTheme

@Composable
internal fun PlayerMenuIconButton(
    modifier: Modifier = Modifier,
    tint: Color = PlayerTheme.colorScheme.iconsMain,
    backgroundColor: Color = Color.Transparent,
    contentDescription: String? = null,
    painter: Painter,
    onClick: (() -> Unit)? = null
) {
    IconButton(
        modifier = modifier
            .size(PlayerTheme.dimens.menuIconSize)
            .background(
                color = backgroundColor,
                shape = CircleShape
            ),
        onClick = { onClick?.invoke() }
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}