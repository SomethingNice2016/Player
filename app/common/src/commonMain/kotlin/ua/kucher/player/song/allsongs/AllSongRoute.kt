package ua.kucher.player.song.allsongs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ua.kucher.player.navigation.AppNavigator

@Composable
internal fun AllSongRoute(
    navigator: AppNavigator,
    presenter: AllSongPresenter
) {

    val uiState by presenter.uiState.collectAsState()

    AllSongScreen(
        uiState = uiState,
        onSongClick = presenter::playSong,
        onRefresh = presenter::refresh,
        onSearch = navigator::navigateToSongSearch,
        onMenuClick = { id ->

        }
    )
}