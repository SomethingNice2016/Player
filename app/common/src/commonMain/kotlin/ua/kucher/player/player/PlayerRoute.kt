package ua.kucher.player.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController

@Composable
internal fun PlayerRoute(
    navController: NavController,
    viewModel: PlayerViewModel,
    content: @Composable () -> Unit = {}
) {

    val current by viewModel.item.collectAsState(null)

    PlayerScreen(
        content = content,
        item = current,
        onForward = viewModel::forward,
        onPrevious = viewModel::back,
        onPlayPause = viewModel::playPause
    )
}