package ua.kucher.player.song.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ua.kucher.player.navigation.AppBackStack
import ua.kucher.player.navigation.AppRoute

@Composable
internal fun FavoriteSongRoute(
    backStack: AppBackStack,
    viewModel: FavoriteSongViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoriteSongScreen(
        uiState = uiState,
        onSongClick = viewModel::playSong,
        onRefresh = viewModel::refresh,
        onBackClick = backStack::removeLast,
        onSearch = {
            backStack.add(AppRoute.SongsSearch)
        },
        onMenuClick = { id ->

        }
    )
}