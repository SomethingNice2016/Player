package ua.kucher.player.album.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ua.kucher.player.navigation.AppBackStack

@Composable
internal fun AlbumSearchRoute(
    backStack: AppBackStack,
    viewModel: AlbumSearchViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AlbumSearchScreen(
        uiState = uiState,
        onBackClick = backStack::removeLast,
        onSearch = viewModel::search,
        onAlbumClick = { id ->

        },
        onMenuClick = { id ->

        }
    )
}