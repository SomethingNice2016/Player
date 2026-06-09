package ua.kucher.player.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
internal fun HomeRoute(
    navController: NavController,
    viewModel: HomeViewModel
) {
    HomeScreen()
}