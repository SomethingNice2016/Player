package ua.kucher.player.artist.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ua.kucher.player.navigation.AppNavigator

@Composable
internal fun ArtistSearchRoute(
    navigator: AppNavigator,
    presenter: ArtistSearchPresenter
) {

    val uiState by presenter.uiState.collectAsState()

    ArtistSearchScreen(
        uiState = uiState,
        onSearch = presenter::search,
        onBackClick = navigator::navigateBack,
        onArtistClick = { id ->

        },
        onMenuClick = { id ->

        },
    )
}