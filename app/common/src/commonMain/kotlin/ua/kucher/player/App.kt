package ua.kucher.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.map
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import ua.kucher.player.navigation.AppRoute
import ua.kucher.player.navigation.PlayerNavigation
import ua.kucher.player.playback.PlaybackController
import ua.kucher.player.songplayer.MusicPlayerRoute
import ua.kucher.player.songplayer.MusicPlayerViewModel
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.BottomBar
import ua.kucher.player.theme.extensions.miniPlayerHeight
import ua.kucher.player.theme.extensions.rememberMiniPlayerPadding

@Preview
@Composable
fun App() = PlayerTheme(useDarkTheme = true) {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStack.collectAsStateWithLifecycle()

    val playerViewModel: MusicPlayerViewModel = koinViewModel()


    val playerPadding = rememberMiniPlayerPadding()

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

