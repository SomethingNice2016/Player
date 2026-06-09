package ua.kucher.player.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import ua.kucher.player.home.HomeRoute
import ua.kucher.player.setting.SettingRoute
import ua.kucher.player.songlist.SongListRoute

@Composable
internal fun PlayerNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: PlayerRoute = PlayerRoute.Home,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.path,
    ) {

        composable(route = PlayerRoute.Home.path) { navBackStackEntry ->
            HomeRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }

        composable(route = PlayerRoute.SongList.path) { navBackStackEntry ->
            SongListRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }

        composable(route = PlayerRoute.Settings.path) { navBackStackEntry ->
            SettingRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }
    }
}