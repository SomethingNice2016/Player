package ua.kucher.player.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ua.kucher.player.navigation.AppNavigator

@Composable
internal fun HomeRoute(
    navigator: AppNavigator,
    presenter: HomePresenter
) {

    val uiState by presenter.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        onSongClick = presenter::playSong,
        onRefresh = presenter::refresh,
        onFavoriteSongsClick = navigator::navigateToFavoriteSongs,
        onSeeAllAlbums = navigator::navigateToAlbumList,
        onSeeAllSongs = navigator::navigateToAllSongs,
        onSeeAllArtists = navigator::navigateToArtistList,
        onArtistClick = { id ->

        },
        showSongMenu = { id ->

        }
    )
}