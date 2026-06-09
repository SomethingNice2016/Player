package ua.kucher.player.theme.typograpgy

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontWeight
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.roboto_black
import player.app.common.generated.resources.roboto_bold
import player.app.common.generated.resources.roboto_light
import player.app.common.generated.resources.roboto_medium
import player.app.common.generated.resources.roboto_regular
import player.app.common.generated.resources.roboto_thin

internal val RobotoFont: FontFamily
    @Composable
    get() = FontFamily(
        Font(Res.font.roboto_thin, FontWeight.W100),
        Font(Res.font.roboto_light, FontWeight.W300),
        Font(Res.font.roboto_regular, FontWeight.W400),
        Font(Res.font.roboto_medium, FontWeight.W500),
        Font(Res.font.roboto_bold, FontWeight.W700),
        Font(Res.font.roboto_black, FontWeight.W900),
    )

// since there is no original roboto 600, the closes font that can be modified to W600 is regular
internal val RobotoFont600: FontFamily
    @Composable
    get() = FontFamily(Font(Res.font.roboto_regular))
