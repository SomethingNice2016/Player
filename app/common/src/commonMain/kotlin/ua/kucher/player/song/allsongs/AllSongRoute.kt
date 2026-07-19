package ua.kucher.player.song.allsongs

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ua.kucher.player.navigation.AppNavigator
import ua.kucher.player.song.menu.SongMenuRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AllSongRoute(
    navigator: AppNavigator,
    presenter: AllSongPresenter
) {

    val uiState by presenter.uiState.collectAsState()

    var showSongMenu by remember {
        mutableStateOf(false)
    }

    var selectedSongId by remember {
        mutableLongStateOf(0L)
    }

    AllSongScreen(
        uiState = uiState,
        onSongClick = presenter::playSong,
        onRefresh = presenter::refresh,
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