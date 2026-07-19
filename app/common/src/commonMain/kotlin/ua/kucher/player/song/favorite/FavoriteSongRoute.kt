package ua.kucher.player.song.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ua.kucher.player.navigation.AppNavigator
import ua.kucher.player.song.menu.SongMenuRoute

@Composable
internal fun FavoriteSongRoute(
    navigator: AppNavigator,
    presenter: FavoriteSongPresenter
) {

    val uiState by presenter.uiState.collectAsState()

    var showSongMenu by remember {
        mutableStateOf(false)
    }

    var selectedSongId by remember {
        mutableLongStateOf(0L)
    }

    FavoriteSongScreen(
        uiState = uiState,
        onSongClick = presenter::playSong,
        onRefresh = presenter::refresh,
        onBackClick = navigator::navigateBack,
        onSearch = navigator::navigateToSongSearch,
        onMenuClick = { id ->
            selectedSongId = id
            showSongMenu = true
        }
    )

    SongMenuRoute(
        songId = selectedSongId,
        showSongMenu = showSongMenu,
        navigator = navigator,
        onDismiss = {
            showSongMenu = false
        }
    )
}