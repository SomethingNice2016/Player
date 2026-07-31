package ua.kucher.player.core.ui.components

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer

@Composable
actual fun FrostedGlass(
    modifier: Modifier,
    enabled: Boolean,
    blurRadius: Float,
    tint: Color,
    content: @Composable (BoxScope.() -> Unit)
) {

    val blurModifier =
        if (enabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Modifier.graphicsLayer {
                renderEffect = BlurEffect(
                    radiusX = blurRadius,
                    radiusY = blurRadius,
                    edgeTreatment = TileMode.Clamp
                )
            }
        } else {
            Modifier
        }

    Box(modifier = modifier) {
        Box(modifier = blurModifier) {
            content()
            if (enabled) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(tint)
                )
            }
        }
    }
}