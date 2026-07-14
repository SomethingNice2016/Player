package ua.kucher.player.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ua.kucher.player.navigation.AppBackStack
import ua.kucher.player.navigation.AppRoute

@Composable
internal fun HomeRoute(
    backStack: AppBackStack,
    viewModel: HomeViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onSongClick = viewModel::playSong,
        onRefresh = viewModel::refresh,
        onFavoriteSongsClick = {
            backStack.add(AppRoute.FavoriteSongs)
        },
        onSeeAllAlbums = {
            backStack.add(AppRoute.AlbumList)
        },
        onSeeAllSongs = {
            backStack.add(AppRoute.AllSong)
        },
        onSeeAllArtists = {
            backStack.add(AppRoute.ArtistList)
        },
        onArtistClick = { id ->

        },
        showSongMenu = { id ->
            backStack.add(AppRoute.SongMenu)
        }
    )
}