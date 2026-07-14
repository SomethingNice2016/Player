package ua.kucher.player.theme.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.map
import org.koin.compose.koinInject
import ua.kucher.player.playback.PlaybackController
import kotlin.math.roundToInt

@Composable
internal fun rememberMiniPlayerPadding() : Dp {

    val miniPlayerPadding = miniPlayerHeight

    return koinInject<PlaybackController>().state.map { state ->
        if(state.currentItemId == null)
            0.dp
        else
            miniPlayerPadding
    }.collectAsStateWithLifecycle(0.dp).value
}

@Composable
internal fun Dp.toPx(): Int {
    return with(LocalDensity.current) {
        toPx().roundToInt()
    }
}