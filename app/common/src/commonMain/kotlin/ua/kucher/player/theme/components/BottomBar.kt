package ua.kucher.player.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ua.kucher.player.navigation.PlayerRoute
import ua.kucher.player.theme.PlayerTheme

@Composable
internal fun BottomBar(
    modifier: Modifier = Modifier,
    current: PlayerRoute,
    onClick: (PlayerRoute) -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = PlayerTheme.dimens.dimens20Px,
                vertical = PlayerTheme.dimens.dimens16Px
            ),

        color = PlayerTheme.colorScheme.primaryBackground,
        shape = PlayerTheme.shapes.radius24Px,
        border = BorderStroke(
            1.dp,
            PlayerTheme.colorScheme.borderMain
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(vertical = PlayerTheme.dimens.dimens14Px),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            PlayerRoute.getMainMenuItems().forEach { item ->

                val selected = item == current

                Box(
                    modifier = Modifier
                        .size(PlayerTheme.dimens.menuIconSize)
                        .background(
                            color = if (selected)
                                PlayerTheme.colorScheme.menuEnableButton.copy(alpha = 0.15f)
                            else
                                Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable { onClick(item) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(requireNotNull(item.icon) { "Bottom menu item icon must not be null!" } ),
                        contentDescription = item.label?.let { labelRes ->
                            stringResource(labelRes)
                        } ?: "",
                        tint = if (selected)
                            PlayerTheme.colorScheme.menuEnableButton
                        else
                            PlayerTheme.colorScheme.menuDisableButton
                    )
                }
            }
        }
    }
}