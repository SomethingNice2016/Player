package ua.kucher.player.artist.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ua.kucher.player.navigation.AppBackStack

@Composable
internal fun ArtistSearchRoute(
    backStack: AppBackStack,
    viewModel: ArtistSearchViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ArtistSearchScreen(
        uiState = uiState,
        onSearch = viewModel::search,
        onArtistClick = { id ->

        },
        onMenuClick = { id ->

        },
        onBackClick = {
            backStack.removeLast()
        }
    )
}