package ua.kucher.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import ua.kucher.player.navigation.AppRoute
import ua.kucher.player.navigation.PlayerNavigation
import ua.kucher.player.player.PlayerRoute
import ua.kucher.player.player.PlayerViewModel
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.BottomBar

@Preview
@Composable
fun App() = PlayerTheme(useDarkTheme = true) {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStack.collectAsState()

    val playerViewModel: PlayerViewModel = koinViewModel()

    val menuItems = remember {
        AppRoute.getMainMenuItems()
    }

    val currentRoute = navBackStackEntry.mapNotNull { backStackEntry ->
        backStackEntry.destination.route?.let { path ->
            AppRoute.getByPath(path)
        }
    }.findLast { route ->
        menuItems.contains(route)
    } ?: AppRoute.Home

    PlayerRoute(
        navController = navController,
        viewModel = playerViewModel
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            PlayerNavigation(
                modifier = Modifier.fillMaxSize(),
                navController = navController
            )

            BottomBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                current = currentRoute,
                items = menuItems,
                onClick = { route ->
                    navController.navigate(route.path) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

