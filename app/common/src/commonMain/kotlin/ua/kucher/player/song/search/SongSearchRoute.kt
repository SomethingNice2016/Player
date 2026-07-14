package ua.kucher.player.song.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ua.kucher.player.navigation.AppBackStack

@Composable
internal fun SongsSearchRoute(
    backStack: AppBackStack,
    viewModel: SongsSearchViewModel,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SongsSearchScreen(
        uiState = uiState,
        onSearch = viewModel::search,
        onSongClick = viewModel::playSong,
        onMenuClick = { id ->

        },
        onBack = {
            backStack.removeLast()
        }
    )

}