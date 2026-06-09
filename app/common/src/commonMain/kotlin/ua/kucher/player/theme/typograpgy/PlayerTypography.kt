package ua.kucher.player.theme.typograpgy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class PlayerTypography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val h5: TextStyle,
    val largeTitle: TextStyle,
    val mediumTitle: TextStyle,
    val smallTitle: TextStyle,
    val subtitle: TextStyle,
    val largeBody: TextStyle,
    val mediumBody: TextStyle,
    val smallBody: TextStyle,
    val largeButton: TextStyle,
    val mediumButton: TextStyle,
    val smallButton: TextStyle,
    val largeInput: TextStyle,
    val mediumInput: TextStyle,
    val chip: TextStyle,
    val caption: TextStyle,
    val largeBadge: TextStyle,
    val link: TextStyle,
    val smallBadge: TextStyle,
    val tabBar: TextStyle,
    val tabBarStrong: TextStyle,
    val m3LargeLabel: TextStyle,
)

internal val playerTypography: PlayerTypography
    @Composable
    get() = PlayerTypography(
        h1 = TextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 38.sp,
            lineHeight = 46.sp,
            fontFamily = RobotoFont600,
        ),
        h2 = TextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 32.sp,
            lineHeight = 36.sp,
            fontFamily = RobotoFont600,
        ),
        h3 = TextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 28.sp,
            lineHeight = 32.sp,
            fontFamily = RobotoFont600,
        ),
        h4 = TextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 24.sp,
            lineHeight = 28.sp,
            fontFamily = RobotoFont600,
        ),
        h5 = TextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            fontFamily = RobotoFont600,
        ),
        largeTitle = TextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 28.sp,
            lineHeight = 32.sp,
            fontFamily = RobotoFont600,
        ),
        mediumTitle = TextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 18.sp,
            lineHeight = 20.sp,
            fontFamily = RobotoFont600,
        ),
        smallTitle = TextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            fontFamily = RobotoFont600,
        ),
        subtitle = TextStyle(
            fontWeight = FontWeight.W400,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            fontFamily = RobotoFont,
        ),
        largeBody = TextStyle(
            fontWeight = FontWeight.W400,
            fontSize = 17.sp,
            lineHeight = 20.sp,
            fontFamily = RobotoFont,
        ),
        mediumBody = TextStyle(
            fontWeight = FontWeight.W400,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            fontFamily = RobotoFont,
        ),
        smallBody = TextStyle(
            fontWeight = FontWeight.W400,
            fontSize = 13.sp,
            lineHeight = 16.sp,
            fontFamily = RobotoFont,
        ),
        largeButton = TextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 17.sp,
            lineHeight = 20.sp,
            fontFamily = RobotoFont600,
        ),
        mediumButton = TextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            fontFamily = RobotoFont600,
        ),
        smallButton = TextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            fontFamily = RobotoFont600,
        ),
        largeInput = TextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 36.sp,
            lineHeight = 40.sp,
            fontFamily = RobotoFont600,
        ),
        mediumInput = TextStyle(
            fontWeight = FontWeight.W400,
            fontSize = 17.sp,
            lineHeight = 20.sp,
            fontFamily = RobotoFont,
        ),
        chip = TextStyle(
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = RobotoFont,
        ),
        caption = TextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            fontFamily = RobotoFont600,
        ),
        largeBadge = TextStyle(
            fontWeight = FontWeight.W400,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = RobotoFont,
        ),
        link = TextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 13.sp,
            lineHeight = 16.sp,
            fontFamily = RobotoFont600,
        ),
        smallBadge = TextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 10.sp,
            lineHeight = 9.sp,
            fontFamily = RobotoFont600,
        ),
        tabBar = TextStyle(
            fontWeight = FontWeight.W400,
            fontSize = 10.sp,
            lineHeight = 12.sp,
            fontFamily = RobotoFont,
        ),
        tabBarStrong = TextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 10.sp,
            lineHeight = 12.sp,
            fontFamily = RobotoFont600,
        ),
        m3LargeLabel = TextStyle(
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
            fontFamily = RobotoFont,
        ),
    )

internal val LocalPlayerTypography: ProvidableCompositionLocal<PlayerTypography>
    @Composable
    get() {
        val typography = playerTypography
        return staticCompositionLocalOf { typography }
    }
