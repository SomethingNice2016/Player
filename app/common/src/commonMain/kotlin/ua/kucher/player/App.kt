package ua.kucher.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import ua.kucher.player.navigation.AppRoute
import ua.kucher.player.navigation.PlayerNavigation
import ua.kucher.player.songplayer.MusicPlayerRoute
import ua.kucher.player.songplayer.MusicPlayerViewModel
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.BottomBar
import ua.kucher.player.theme.extensions.rememberMiniPlayerPadding

@Preview
@Composable
fun App() = PlayerTheme(useDarkTheme = true) {

    val navController = rememberNavController()

    val playerViewModel: MusicPlayerViewModel = koinViewModel()

    val playerPadding = rememberMiniPlayerPadding()

    val currentRoute = AppRoute.mainMenuItems.firstOrNull { route ->
        navController.currentDestination?.hasRoute(route::class) == true
    } ?: AppRoute.Home

    MusicPlayerRoute(
        navController = navController,
        viewModel = playerViewModel
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            PlayerNavigation(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = playerPadding),
                navController = navController
            )

            BottomBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                current = currentRoute,
                items = AppRoute.mainMenuItems,
                onClick = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

