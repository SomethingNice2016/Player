package ua.kucher.player.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import ua.kucher.player.theme.color.LocalPlayerColorScheme
import ua.kucher.player.theme.color.PlayerColorScheme
import ua.kucher.player.theme.color.playerDarkColorScheme
import ua.kucher.player.theme.color.playerLightColorScheme
import ua.kucher.player.theme.typograpgy.LocalPlayerTypography
import ua.kucher.player.theme.typograpgy.PlayerTypography
import ua.kucher.player.theme.typograpgy.playerTypography

internal val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }

@Composable
internal fun PlayerTheme(
    useDarkTheme: Boolean? = null,
    content: @Composable () -> Unit
) {

    val colorScheme = if (useDarkTheme ?: isSystemInDarkTheme())
        playerDarkColorScheme()
    else
        playerLightColorScheme()

    CompositionLocalProvider(
        LocalPlayerTypography provides playerTypography,
        LocalPlayerColorScheme provides colorScheme,
        LocalIndication provides ripple(
            bounded = true,
            color = PlayerTheme.colorScheme.rippleColor
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PlayerTheme.colorScheme.primaryBackground)
        ) {
            content.invoke()
        }
    }
}

object PlayerTheme {
    val colorScheme: PlayerColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalPlayerColorScheme.current

    val shapes: PlayerShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalPlayerShapes.current

    val typography: PlayerTypography
        @Composable
        get() = LocalPlayerTypography.current

    val dimens: PlayerDimens
        @Composable
        @ReadOnlyComposable
        get() = LocalPlayerDimens.current
}

