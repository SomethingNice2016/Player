package ua.kucher.player.album.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ua.kucher.player.navigation.AppRouter
import ua.kucher.player.navigation.navigateToAlbum
import ua.kucher.player.navigation.navigateToAlbumSearch

@Composable
internal fun AlbumListRoute(
    navigator: AppRouter,
    presenter: AlbumListPresenter
) {

    val uiState by presenter.uiState.collectAsState()

    AlbumListScreen(
        uiState = uiState,
        onRefresh = presenter::refresh,
        onBackClick = navigator::navigateBack,
        onSearch = navigator::navigateToAlbumSearch,
        onAlbumClick = navigator::navigateToAlbum,
        onMenuClick = { id ->

        }
    )
}