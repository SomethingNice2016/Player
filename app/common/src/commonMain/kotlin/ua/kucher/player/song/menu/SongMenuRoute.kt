package ua.kucher.player.song.menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
internal fun SongMenuRoute(
    navController: NavController,
    viewModel: SongMenuViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SongMenuDialog(
        uiState = uiState,
        onBackClick = navController::popBackStack,
        onPlayNextClick = {

        },
        setFavoriteState = {

        }
    )

}