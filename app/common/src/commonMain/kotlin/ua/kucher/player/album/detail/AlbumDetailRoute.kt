package ua.kucher.player.album.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ua.kucher.player.navigation.AppRouter
import ua.kucher.player.song.menu.SongMenuRoute

@Composable
internal fun AlbumDetailRoute(
    router: AppRouter,
    presenter: AlbumDetailPresenter
) {

    val uiState by presenter.uiState.collectAsState()

    var showSongMenu by remember {
        mutableStateOf(false)
    }

    var selectedSongId by remember {
        mutableLongStateOf(0L)
    }

    AlbumDetailScreen(
        uiState = uiState,
        onBackClick = router::navigateBack,
        onSongClick = presenter::play,
        onPlayClick = presenter::play,
        onMenuClick = { songId ->
            selectedSongId = songId
            showSongMenu = true
        }
    )

    SongMenuRoute(
        songId = selectedSongId,
        showSongMenu = showSongMenu,
        showAlbumsItem = false,
        router = router,
        onDismiss = {
            showSongMenu = false
        }
    )
}