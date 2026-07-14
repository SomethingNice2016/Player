package ua.kucher.player.song.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ua.kucher.player.navigation.AppRoute

@Composable
internal fun FavoriteSongRoute(
    navController: NavController,
    viewModel: FavoriteSongViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoriteSongScreen(
        uiState = uiState,
        onSongClick = viewModel::playSong,
        onRefresh = viewModel::refresh,
        onBackClick = navController::popBackStack,
        onSearch = {
            navController.navigate(AppRoute.SongsSearch.path)
        },
        onMenuClick = { id ->

        }
    )
}