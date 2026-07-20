package ua.kucher.player.core.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor

@Composable
fun Dot(
    modifier: Modifier = Modifier,
    color: Color
) = Canvas(modifier = modifier) {
    drawCircle(brush = SolidColor(value = color))
}