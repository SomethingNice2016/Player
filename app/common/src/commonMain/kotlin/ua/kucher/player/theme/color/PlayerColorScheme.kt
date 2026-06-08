package ua.kucher.player.theme.color

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import ua.kucher.player.theme.PlayerTheme

@Immutable
data class PlayerColorScheme(
    val primaryBackground: Color,
    val seekbarProgressColor: Color,
    val seekbarColor: Color,
    val menuEnableButton: Color,
    val menuDisableButton: Color,
    val favoriteEnableButton: Color,
    val primaryTextColor: Color,
    val secondaryTextColor: Color
) {
    @OptIn(ExperimentalMaterial3Api::class)
    internal var defaultTopAppBarColorsCached: TopAppBarColors? = null
}

internal fun playerLightColorScheme() = PlayerColorScheme(
    primaryBackground = PlayerPaletteTokens.MidnightBlue,
    seekbarProgressColor = PlayerPaletteTokens.RoyalPurple,
    seekbarColor = PlayerPaletteTokens.White,
    menuEnableButton = PlayerPaletteTokens.RoyalPurple,
    menuDisableButton = PlayerPaletteTokens.MediumGray,
    favoriteEnableButton = PlayerPaletteTokens.BrightRed,
    primaryTextColor = PlayerPaletteTokens.White,
    secondaryTextColor = PlayerPaletteTokens.MediumGray
)

internal fun playerDarkColorScheme() = PlayerColorScheme(
    primaryBackground = PlayerPaletteTokens.MidnightBlue,
    seekbarProgressColor = PlayerPaletteTokens.RoyalPurple,
    seekbarColor = PlayerPaletteTokens.White,
    menuEnableButton = PlayerPaletteTokens.RoyalPurple,
    menuDisableButton = PlayerPaletteTokens.MediumGray,
    favoriteEnableButton = PlayerPaletteTokens.BrightRed,
    primaryTextColor = PlayerPaletteTokens.White,
    secondaryTextColor = PlayerPaletteTokens.MediumGray
)

internal val LocalPlayerColorScheme = staticCompositionLocalOf {
    playerLightColorScheme()
}
