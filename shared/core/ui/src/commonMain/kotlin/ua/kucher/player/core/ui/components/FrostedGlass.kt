package ua.kucher.player.core.ui.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
expect fun FrostedGlass(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    blurRadius: Float = 10F,
    tint: Color = Color.White.copy(alpha = 0.15f),
    content: @Composable BoxScope.() -> Unit
)

