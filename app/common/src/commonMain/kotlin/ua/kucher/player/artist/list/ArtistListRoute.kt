package ua.kucher.player.artist.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ua.kucher.player.navigation.AppBackStack
import ua.kucher.player.navigation.AppRoute

@Composable
internal fun ArtistListRoute(
    backStack: AppBackStack,
    viewModel: ArtistListViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ArtistListScreen(
        uiState = uiState,
        onRefresh = viewModel::refresh,
        onBackClick = backStack::removeLast,
        onArtistClick = { id ->

        },
        onMenuClick = { id ->

        },
        onSearch = {
            backStack.add(AppRoute.ArtistSearch)
        },
    )
}