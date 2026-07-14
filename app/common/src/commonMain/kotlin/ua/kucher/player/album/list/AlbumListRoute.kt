package ua.kucher.player.album.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ua.kucher.player.navigation.AppRoute

@Composable
internal fun AlbumListRoute(
    navController: NavController,
    viewModel: AlbumListViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AlbumListScreen(
        uiState = uiState,
        onRefresh = viewModel::refresh,
        onBackClick = navController::popBackStack,
        onSearch = {
            navController.navigate(AppRoute.AlbumSearch)
        },
        onAlbumClick = { id ->

        },
        onMenuClick = { id ->

        }
    )
}