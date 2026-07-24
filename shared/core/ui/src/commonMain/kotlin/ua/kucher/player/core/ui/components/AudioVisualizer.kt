package ua.kucher.player.core.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed

@Composable
fun AudioVisualizer(
    modifier: Modifier = Modifier,
    barsCount: Int = 20,
    isPlaying: Boolean,
    color: Color = Color.Green
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val animations = remember(barsCount) {
        List(barsCount) { index ->
            index
        }
    }.map { index ->
        infiniteTransition.animateFloat(
            initialValue = 0.2F,
            targetValue = 1F,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 300 + index * 40
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bar_$index"
        )
    }

    val frozenHeights = remember(barsCount) {
        MutableList(barsCount) { 0.2F }
    }

    Canvas(modifier = modifier) {

        val spacing = 3.dp.toPx()

        val availableWidth = size.width

        val barWidth =
            ((availableWidth - spacing * (barsCount - 1)) / barsCount)
                .coerceAtLeast(2.dp.toPx())

        val contentWidth =
            barsCount * barWidth +
                    (barsCount - 1) * spacing

        val startX =
            ((availableWidth - contentWidth) / 2F)
                .coerceAtLeast(0F)

        animations.fastForEachIndexed { index, animation ->

            if (isPlaying) {
                frozenHeights[index] = animation.value
            }

            val heightFactor = frozenHeights[index]

            val barHeight = size.height * heightFactor

            drawRoundRect(
                color = color,
                topLeft = Offset(
                    x = startX + index * (barWidth + spacing),
                    y = (size.height - barHeight) / 2F
                ),
                size = Size(
                    width = barWidth,
                    height = barHeight
                ),
                cornerRadius = CornerRadius(
                    x = barWidth / 2F,
                    y = barWidth / 2F
                )
            )
        }
    }
}