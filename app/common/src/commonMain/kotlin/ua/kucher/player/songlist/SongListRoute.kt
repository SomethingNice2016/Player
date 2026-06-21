package ua.kucher.player.songlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController

@Composable
internal fun SongListRoute(
    navController: NavController,
    viewModel: SongListViewModel
) {

    val uiState by viewModel.uiState.collectAsState()

    SongListScreen(
        uiState = uiState,
        onSongClick = viewModel::playSong,
        onRefresh = viewModel::refresh
    )
}