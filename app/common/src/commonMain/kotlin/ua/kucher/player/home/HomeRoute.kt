package ua.kucher.player.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ua.kucher.player.navigation.AppRouter
import ua.kucher.player.navigation.navigateToAlbumList
import ua.kucher.player.navigation.navigateToAllSongs
import ua.kucher.player.navigation.navigateToArtist
import ua.kucher.player.navigation.navigateToArtistList
import ua.kucher.player.navigation.navigateToFavoriteSongs
import ua.kucher.player.song.menu.SongMenuRoute

@Composable
internal fun HomeRoute(
    router: AppRouter,
    presenter: HomePresenter
) {

    val uiState by presenter.uiState.collectAsState()

    var showSongMenu by remember {
        mutableStateOf(false)
    }

    var selectedSongId by remember {
        mutableLongStateOf(0L)
    }

    HomeScreen(
        uiState = uiState,
        onSongClick = presenter::playSong,
        onRefresh = presenter::refresh,
        onFavoriteSongsClick = router::navigateToFavoriteSongs,
        onSeeAllAlbums = router::navigateToAlbumList,
        onSeeAllSongs = router::navigateToAllSongs,
        onSeeAllArtists = router::navigateToArtistList,
        onArtistClick = router::navigateToArtist,
        showSongMenu = { id ->
            selectedSongId = id
            showSongMenu = true
        }
    )

    SongMenuRoute(
        songId = selectedSongId,
        showSongMenu = showSongMenu,
        router = router,
        onDismiss = {
            showSongMenu = false
        }
    )
}