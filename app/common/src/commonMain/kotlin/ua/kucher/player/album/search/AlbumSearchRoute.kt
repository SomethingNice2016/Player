package ua.kucher.player.album.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ua.kucher.player.navigation.AppRouter
import ua.kucher.player.navigation.navigateToAlbum

@Composable
internal fun AlbumSearchRoute(
    router: AppRouter,
    presenter: AlbumSearchPresenter
) {

    val uiState by presenter.uiState.collectAsState()

    AlbumSearchScreen(
        uiState = uiState,
        onSearch = presenter::search,
        onBackClick = router::navigateBack,
        onAlbumClick = router::navigateToAlbum,
        onMenuClick = { id ->

        }
    )
}