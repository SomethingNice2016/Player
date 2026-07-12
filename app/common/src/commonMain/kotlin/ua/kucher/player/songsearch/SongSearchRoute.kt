package ua.kucher.player.songsearch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
internal fun SongsSearchRoute(
    viewModel: SongsSearchViewModel,
    navController: NavController
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SongsSearchScreen(
        uiState = uiState,
        onSearch = viewModel::search,
        onSongClick = viewModel::playSong,
        onMenuClick = { id ->

        },
        onBack = {
            navController.popBackStack()
        }
    )

}