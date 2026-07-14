package ua.kucher.player.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ua.kucher.player.navigation.AppRoute
import ua.kucher.player.navigation.icon
import ua.kucher.player.navigation.label
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.items.PlayerMenuIconButton

@Composable
internal fun BottomBar(
    modifier: Modifier = Modifier,
    items: List<AppRoute>,
    current: AppRoute,
    onClick: (AppRoute) -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = PlayerTheme.colorScheme.secondaryBackground,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .navigationBarsPadding()
                .padding(vertical = PlayerTheme.dimens.dimens8Px),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            items.forEach { item ->

                val selected = item == current
                val background: Color
                val tint: Color

                if (selected) {
                    background = PlayerTheme.colorScheme.rippleColor
                    tint = PlayerTheme.colorScheme.menuEnableButton
                } else {
                    background = Color.Transparent
                    tint = PlayerTheme.colorScheme.menuDisableButton
                }

                PlayerMenuIconButton(
                    backgroundColor = background,
                    tint = tint,
                    painter = painterResource(requireNotNull(item.icon) { "Bottom menu item icon must not be null!" }),
                    contentDescription = item.label?.let { res -> stringResource(res) } ?: "",
                    onClick = { onClick(item) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun BottomMenuPreview() {
    BottomBar(
        modifier = Modifier.fillMaxWidth(),
        items = AppRoute.mainMenuItems,
        current = AppRoute.AllSong,
        onClick = {}
    )
}