package ua.kucher.player.album.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ua.kucher.player.navigation.AppNavigator

@Composable
internal fun AlbumListRoute(
    navigator: AppNavigator,
    presenter: AlbumListPresenter
) {

    val uiState by presenter.uiState.collectAsState()

    AlbumListScreen(
        uiState = uiState,
        onRefresh = presenter::refresh,
        onBackClick = navigator::navigateBack,
        onSearch = navigator::navigateToAlbumSearch,
        onAlbumClick = { id ->

        },
        onMenuClick = { id ->

        }
    )
}