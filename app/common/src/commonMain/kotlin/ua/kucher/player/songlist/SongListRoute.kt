package ua.kucher.player.songlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ua.kucher.player.navigation.AppRoute

@Composable
internal fun SongListRoute(
    navController: NavController,
    viewModel: SongListViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SongListScreen(
        uiState = uiState,
        onSongClick = viewModel::playSong,
        onRefresh = viewModel::refresh,
        onSearch = {
            navController.navigate(AppRoute.Search.path)
        }
    )
}