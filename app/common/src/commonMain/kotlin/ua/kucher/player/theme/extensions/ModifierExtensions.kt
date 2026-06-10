package ua.kucher.player.theme.extensions

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import ua.kucher.player.theme.PlayerTheme

private val bottomNavHeight: Dp
    @Composable
    get() = PlayerTheme.dimens.dimens8Px * 3 + PlayerTheme.dimens.menuIconSize


@Composable
internal fun Modifier.bottomNavPaddings() =
    padding(bottom = bottomNavHeight)

@Composable
internal fun BottomNavSpacer() {
    Spacer(modifier = Modifier.height(bottomNavHeight))
}
