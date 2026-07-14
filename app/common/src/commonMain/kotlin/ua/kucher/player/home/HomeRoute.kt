package ua.kucher.player.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ua.kucher.player.navigation.AppRoute
import ua.kucher.player.navigation.navigateTo

@Composable
internal fun HomeRoute(
    navController: NavController,
    viewModel: HomeViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onSongClick = viewModel::playSong,
        onRefresh = viewModel::refresh,
        onFavoriteSongsClick = {
            navController.navigateTo(AppRoute.FavoriteSongs)
        },
        onSeeAllAlbums = {
            navController.navigateTo(AppRoute.AlbumList)
        },
        onSeeAllSongs = {
            navController.navigateTo(AppRoute.AllSong)
        },
        onSeeAllArtists = {
            navController.navigateTo(AppRoute.ArtistList)
        },
        onArtistClick = { id ->

        },
    )
}