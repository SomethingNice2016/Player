package ua.kucher.player.artistlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ua.kucher.player.navigation.AppRoute
import ua.kucher.player.navigation.navigateTo

@Composable
internal fun ArtistListRoute(
    navController: NavController,
    viewModel: ArtistListViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ArtistListScreen(
        uiState = uiState,
        onRefresh = viewModel::refresh,
        onBackClick = navController::popBackStack,
        onArtistClick = { id ->

        },
        onMenuClick = { id ->

        },
        onSearch = {
            navController.navigateTo(AppRoute.ArtistSearch)
        },
    )
}