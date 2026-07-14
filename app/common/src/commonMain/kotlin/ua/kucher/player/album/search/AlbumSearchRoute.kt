package ua.kucher.player.album.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ua.kucher.player.navigation.AppNavigator

@Composable
internal fun AlbumSearchRoute(
    navigator: AppNavigator,
    presenter: AlbumSearchPresenter
) {

    val uiState by presenter.uiState.collectAsState()

    AlbumSearchScreen(
        uiState = uiState,
        onBackClick = navigator::navigateBack,
        onSearch = presenter::search,
        onAlbumClick = { id ->

        },
        onMenuClick = { id ->

        }
    )
}