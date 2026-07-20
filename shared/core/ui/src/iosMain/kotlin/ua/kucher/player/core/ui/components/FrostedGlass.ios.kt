package ua.kucher.player.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.UIKitView
import platform.UIKit.UIBlurEffect
import platform.UIKit.UIBlurEffectStyle
import platform.UIKit.UIVisualEffectView

@Composable
actual fun FrostedGlass(
    modifier: Modifier,
    enabled: Boolean,
    blurRadius: Float,
    tint: Color,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier) {
        if (enabled) {
            UIKitView(
                factory = {
                    val blurEffect = UIBlurEffect.effectWithStyle(
                        UIBlurEffectStyle.UIBlurEffectStyleSystemMaterial
                    )

                    val blurView = UIVisualEffectView(effect = blurEffect)

                    blurView.backgroundColor = tint.toUIColor()

                    blurView
                },
                modifier = Modifier.matchParentSize()
            )
        }

        Box(
            Modifier.matchParentSize()
        ) {
            content()
        }
    }
}