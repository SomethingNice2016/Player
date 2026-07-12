package ua.kucher.player.artistsearch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
internal fun ArtistSearchRoute(
    navController: NavController,
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
            navController.popBackStack()
        }
    )
}