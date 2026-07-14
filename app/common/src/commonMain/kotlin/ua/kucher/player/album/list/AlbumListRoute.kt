package ua.kucher.player.album.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ua.kucher.player.navigation.AppBackStack
import ua.kucher.player.navigation.AppRoute

@Composable
internal fun AlbumListRoute(
    backStack: AppBackStack,
    viewModel: AlbumListViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AlbumListScreen(
        uiState = uiState,
        onRefresh = viewModel::refresh,
        onBackClick = backStack::removeLast,
        onSearch = {
            backStack.add(AppRoute.AlbumSearch)
        },
        onAlbumClick = { id ->

        },
        onMenuClick = { id ->

        }
    )
}