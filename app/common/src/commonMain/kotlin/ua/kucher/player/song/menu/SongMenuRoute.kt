package ua.kucher.player.song.menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ua.kucher.player.navigation.AppNavigator

@Composable
internal fun SongMenuRoute(
    navigator: AppNavigator,
    viewModel: SongMenuPresenter
) {

    val uiState by viewModel.uiState.collectAsState()

    SongMenuDialog(
        uiState = uiState,
        onBackClick = navigator::navigateBack,
        onPlayNextClick = {

        },
        setFavoriteState = {

        }
    )

}