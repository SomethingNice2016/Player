package ua.kucher.player.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
class PlayerDimens(
    val dimens2Px: Dp = 2.dp,
    val dimens4Px: Dp = 4.dp,
    val dimens6Px: Dp = 6.dp,
    val dimens8Px: Dp = 8.dp,
    val dimens10Px: Dp = 10.dp,
    val dimens12Px: Dp = 12.dp,
    val dimens14Px: Dp = 14.dp,
    val dimens16Px: Dp = 16.dp,
    val dimens20Px: Dp = 20.dp,
    val dimens24Px: Dp = 24.dp,
    val dimens32Px: Dp = 32.dp,
    val dimens36Px: Dp = 36.dp,
    val dimens40Px: Dp = 40.dp,
    val dimens60Px: Dp = 60.dp,
    val dimens80Px: Dp = 80.dp,
    val menuIconSize: Dp = 48.dp,
    val songIconSize: Dp = 50.dp,
    val settingItemSize: Dp = 24.dp,
    val seekbarThumbSize: Dp = 16.dp,
    val seekbarTrackSize: Dp = 4.dp
)

internal val LocalPlayerDimens = staticCompositionLocalOf { PlayerDimens() }
