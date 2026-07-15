package ua.kucher.player.core.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> ObserveOneTimeEvents(
    flow: Flow<T>,
    onEvent: suspend (T) -> Unit,
) {

    LaunchedEffect(Unit) {
        flow.collect {
            onEvent(it)
        }
    }
}
