package ua.kucher.player.theme.color

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

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
)

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
