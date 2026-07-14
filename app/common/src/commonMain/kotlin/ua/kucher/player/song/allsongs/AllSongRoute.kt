package ua.kucher.player.song.allsongs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ua.kucher.player.navigation.AppRoute

@Composable
internal fun AllSongRoute(
    navController: NavController,
    viewModel: AllSongViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AllSongScreen(
        uiState = uiState,
        onSongClick = viewModel::playSong,
        onRefresh = viewModel::refresh,
        onSearch = {
            navController.navigate(AppRoute.SongsSearch)
        },
        onMenuClick = { id ->

        }
    )
}