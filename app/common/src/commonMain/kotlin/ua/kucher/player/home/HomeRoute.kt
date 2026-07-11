package ua.kucher.player.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ua.kucher.player.navigation.AppRoute

@Composable
internal fun HomeRoute(
    navController: NavController,
    viewModel: HomeViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onRefresh = {},
        onSearch = {
            navController.navigate(AppRoute.Search.path)
        },
    )
}