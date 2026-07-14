package ua.kucher.player.song.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ua.kucher.player.navigation.AppNavigator

@Composable
internal fun SongsSearchRoute(
    navigator: AppNavigator,
    presenter: SongsSearchPresenter,
) {

    val uiState by presenter.uiState.collectAsState()

    SongsSearchScreen(
        uiState = uiState,
        onSearch = presenter::search,
        onSongClick = presenter::playSong,
        onBackClick = navigator::navigateBack,
        onMenuClick = { id ->

        },
    )

}