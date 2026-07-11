package ua.kucher.player.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
internal fun SearchRoute(
    viewModel: SearchViewModel,
    navController: NavController
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchScreen(
        uiState = uiState,
        onSearch = viewModel::search,
        onSongClick = viewModel::playSong,
        onBack = {
            navController.popBackStack()
        }
    )

}