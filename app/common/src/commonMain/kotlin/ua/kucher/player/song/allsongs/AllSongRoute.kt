package ua.kucher.player.song.allsongs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ua.kucher.player.navigation.AppBackStack
import ua.kucher.player.navigation.AppRoute

@Composable
internal fun AllSongRoute(
    backStack: AppBackStack,
    viewModel: AllSongViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AllSongScreen(
        uiState = uiState,
        onSongClick = viewModel::playSong,
        onRefresh = viewModel::refresh,
        onSearch = {
            backStack.add(AppRoute.SongsSearch)
        },
        onMenuClick = { id ->

        }
    )
}