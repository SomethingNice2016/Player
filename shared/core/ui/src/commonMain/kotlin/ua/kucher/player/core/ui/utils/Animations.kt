package ua.kucher.player.core.ui.utils

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable

@Composable
fun rememberPulseAnimation(
    initialValue: Float = 1F,
    targetValue: Float = 0F,
    duration: Int = 500
): Float {
    val transition = rememberInfiniteTransition(label = "Pulse")
    return transition.animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = duration
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "Pulse"
    ).value
}