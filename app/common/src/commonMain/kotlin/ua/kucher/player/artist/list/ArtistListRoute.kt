package ua.kucher.player.artist.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ua.kucher.player.navigation.AppNavigator

@Composable
internal fun ArtistListRoute(
    navigator: AppNavigator,
    presenter: ArtistListPresenter
) {

    val uiState by presenter.uiState.collectAsState()

    ArtistListScreen(
        uiState = uiState,
        onRefresh = presenter::refresh,
        onBackClick = navigator::navigateBack,
        onSearch = navigator::navigateToArtistSearch,
        onArtistClick = { id ->

        },
        onMenuClick = { id ->

        },
    )
}