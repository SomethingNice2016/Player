package ua.kucher.player.album.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
internal fun AlbumSearchRoute(
    navController: NavController,
    viewModel: AlbumSearchViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AlbumSearchScreen(
        uiState = uiState,
        onBackClick = navController::popBackStack,
        onSearch = viewModel::search,
        onAlbumClick = { id ->

        },
        onMenuClick = { id ->

        }
    )
}