package ua.kucher.player.songplayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
internal fun MusicPlayerRoute(
    modifier: Modifier = Modifier,
    viewModel: MusicPlayerViewModel,
    content: @Composable () -> Unit = {}
) {

    val state by viewModel.uiState.collectAsState(null)

    MusicPlayerScreen(
        modifier = modifier,
        content = content,
        state = state,
        onPlay = viewModel::playById,
        onForward = viewModel::forward,
        onPrevious = viewModel::back,
        onPlayPause = viewModel::playPause,
        onShuffle = viewModel::shuffle,
        onRepeat = viewModel::repeat,
        onSeek = viewModel::seekToPosition,
    )
}