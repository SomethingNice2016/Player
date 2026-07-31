package ua.kucher.player.theme.components.bottombar

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
import androidx.compose.ui.util.fastForEach
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.items.PlayerMenuIconButton

@Composable
internal fun <T : MenuItem> BottomBar(
    modifier: Modifier = Modifier,
    items: List<T>,
    isSelected: (T) -> Boolean,
    onClick: (T) -> Unit
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
            items.fastForEach { item ->

                val background: Color
                val tint: Color

                if (isSelected(item)) {
                    background = PlayerTheme.colorScheme.rippleColor
                    tint = PlayerTheme.colorScheme.menuEnableButton
                } else {
                    background = Color.Transparent
                    tint = PlayerTheme.colorScheme.menuDisableButton
                }

                PlayerMenuIconButton(
                    backgroundColor = background,
                    tint = tint,
                    imageVector = vectorResource(item.icon),
                    contentDescription = stringResource(item.label),
                    onClick = { onClick(item) }
                )
            }
        }
    }
}