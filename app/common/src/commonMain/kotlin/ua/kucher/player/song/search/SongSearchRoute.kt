package ua.kucher.player.song.search

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
internal fun SongsSearchRoute(
    navigator: AppNavigator,
    presenter: SongsSearchPresenter,
) {

    val uiState by presenter.uiState.collectAsState()

    var showSongMenu by remember {
        mutableStateOf(false)
    }

    var selectedSongId by remember {
        mutableLongStateOf(0L)
    }

    SongsSearchScreen(
        uiState = uiState,
        onSearch = presenter::search,
        onSongClick = presenter::playSong,
        onBackClick = navigator::navigateBack,
        onMenuClick = { id ->
            selectedSongId = id
            showSongMenu = true
        },
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