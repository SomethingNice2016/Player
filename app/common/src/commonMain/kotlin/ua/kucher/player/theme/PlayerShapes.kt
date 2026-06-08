package ua.kucher.player.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
class PlayerShapes(
    val radius2Px: CornerBasedShape = RoundedCornerShape(2.0.dp),
    val radius4Px: CornerBasedShape = RoundedCornerShape(4.0.dp),
    val radius6Px: CornerBasedShape = RoundedCornerShape(6.0.dp),
    val radius8Px: CornerBasedShape = RoundedCornerShape(8.0.dp),
    val radius10Px: CornerBasedShape = RoundedCornerShape(10.0.dp),
    val radius12Px: CornerBasedShape = RoundedCornerShape(12.0.dp),
    val radius16Px: CornerBasedShape = RoundedCornerShape(16.0.dp),
    val radius20Px: CornerBasedShape = RoundedCornerShape(20.0.dp),
    val radius24Px: CornerBasedShape = RoundedCornerShape(24.0.dp),
    val radius32Px: CornerBasedShape = RoundedCornerShape(32.0.dp),
    val radius36Px: CornerBasedShape = RoundedCornerShape(36.0.dp),
    val radius40Px: CornerBasedShape = RoundedCornerShape(40.0.dp),
    val radius60Px: CornerBasedShape = RoundedCornerShape(60.0.dp),
    val radius80Px: CornerBasedShape = RoundedCornerShape(80.0.dp),
)

internal val LocalPlayerShapes = staticCompositionLocalOf { PlayerShapes() }
