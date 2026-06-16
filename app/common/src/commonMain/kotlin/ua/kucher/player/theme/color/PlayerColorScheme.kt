package ua.kucher.player.theme.color

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class PlayerColorScheme(
    val primaryBackground: Color,
    val secondaryBackground: Color,
    val seekbarProgressColor: Color,
    val seekbarColor: Color,
    val menuEnableButton: Color,
    val menuDisableButton: Color,
    val favoriteEnableButton: Color,
    val primaryTextColor: Color,
    val secondaryTextColor: Color,
    val rippleColor: Color,
    val borderMain: Color,
    val iconsMain: Color
)

internal fun playerLightColorScheme() = PlayerColorScheme(
    primaryBackground = PlayerPaletteTokens.GhostWhite,
    secondaryBackground = PlayerPaletteTokens.MidnightBlueSurface,
    seekbarProgressColor = PlayerPaletteTokens.RoyalPurple,
    seekbarColor = PlayerPaletteTokens.Black,
    menuEnableButton = PlayerPaletteTokens.RoyalPurple,
    menuDisableButton = PlayerPaletteTokens.MediumGray,
    favoriteEnableButton = PlayerPaletteTokens.BrightRed,
    primaryTextColor = PlayerPaletteTokens.CharcoalBlack,
    secondaryTextColor = PlayerPaletteTokens.MediumGray,
    rippleColor = PlayerPaletteTokens.RoyalPurple.copy(alpha = 0.15F),
    borderMain = PlayerPaletteTokens.Platinum,
    iconsMain = PlayerPaletteTokens.CharcoalBlack
)

internal fun playerDarkColorScheme() = PlayerColorScheme(
    primaryBackground = PlayerPaletteTokens.MidnightBlue,
    secondaryBackground = PlayerPaletteTokens.MidnightBlueSurface,
    seekbarProgressColor = PlayerPaletteTokens.White,
    seekbarColor = PlayerPaletteTokens.White.copy(alpha = 0.5F),
    menuEnableButton = PlayerPaletteTokens.RoyalPurple,
    menuDisableButton = PlayerPaletteTokens.MediumGray,
    favoriteEnableButton = PlayerPaletteTokens.BrightRed,
    primaryTextColor = PlayerPaletteTokens.White,
    secondaryTextColor = PlayerPaletteTokens.MediumGray,
    rippleColor = PlayerPaletteTokens.RoyalPurple.copy(alpha = 0.15F),
    borderMain = PlayerPaletteTokens.White.copy(alpha = 0.25F),
    iconsMain = PlayerPaletteTokens.White
)

internal val LocalPlayerColorScheme = staticCompositionLocalOf {
    playerDarkColorScheme()
}
