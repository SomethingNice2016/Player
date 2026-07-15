package ua.kucher.player.core.common.utils

import androidx.compose.ui.unit.Dp
import kotlin.math.roundToInt

fun lerp(
    start: Dp,
    stop: Dp,
    fraction: Float
) = start + (stop - start) * fraction

fun lerp(
    start: Int,
    stop: Int,
    fraction: Float
) = (start + (stop - start) * fraction).roundToInt()

fun lerp(
    start: Float,
    stop: Float,
    fraction: Float
) = start + (stop - start) * fraction
