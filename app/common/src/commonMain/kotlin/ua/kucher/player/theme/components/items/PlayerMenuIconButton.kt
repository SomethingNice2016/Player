package ua.kucher.player.theme.components.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import ua.kucher.player.theme.PlayerTheme

@Composable
internal fun PlayerMenuIconButton(
    modifier: Modifier = Modifier,
    tint: Color = PlayerTheme.colorScheme.iconsMain,
    backgroundColor: Color = Color.Transparent,
    iconSize: Dp = PlayerTheme.dimens.dimens24Px,
    buttonSize: Dp = PlayerTheme.dimens.menuIconSize,
    contentDescription: String? = null,
    imageVector: ImageVector,
    onClick: (() -> Unit)? = null
) {
    IconButton(
        modifier = modifier
            .size(buttonSize)
            .background(
                color = backgroundColor,
                shape = CircleShape
            ),
        onClick = { onClick?.invoke() }
    ) {
        Icon(
            modifier = Modifier.size(iconSize),
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}