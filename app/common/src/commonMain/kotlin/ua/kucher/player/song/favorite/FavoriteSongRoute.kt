package ua.kucher.player.song.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ua.kucher.player.navigation.AppNavigator

@Composable
internal fun FavoriteSongRoute(
    navigator: AppNavigator,
    presenter: FavoriteSongPresenter
) {

    val uiState by presenter.uiState.collectAsState()

    FavoriteSongScreen(
        uiState = uiState,
        onSongClick = presenter::playSong,
        onRefresh = presenter::refresh,
        onBackClick = navigator::navigateBack,
        onSearch = navigator::navigateToSongSearch,
        onMenuClick = { id ->

        }
    )
}