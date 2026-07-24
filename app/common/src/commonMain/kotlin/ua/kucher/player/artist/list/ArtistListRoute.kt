package ua.kucher.player.artist.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ua.kucher.player.navigation.AppRouter
import ua.kucher.player.navigation.navigateToArtist
import ua.kucher.player.navigation.navigateToArtistSearch

@Composable
internal fun ArtistListRoute(
    router: AppRouter,
    presenter: ArtistListPresenter
) {

    val uiState by presenter.uiState.collectAsState()

    ArtistListScreen(
        uiState = uiState,
        onRefresh = presenter::refresh,
        onBackClick = router::navigateBack,
        onSearch = router::navigateToArtistSearch,
        onArtistClick = router::navigateToArtist,
        onMenuClick = { id ->

        },
    )
}