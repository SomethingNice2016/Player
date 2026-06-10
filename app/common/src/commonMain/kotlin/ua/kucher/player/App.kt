package ua.kucher.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ua.kucher.player.navigation.PlayerNavigation
import ua.kucher.player.navigation.PlayerRoute
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.BottomBar

@Preview
@Composable
fun App() = PlayerTheme(useDarkTheme = true) {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route?.let { path ->
        PlayerRoute.getByPath(path)
    } ?: PlayerRoute.Home

    Box(modifier = Modifier.fillMaxSize()) {
        PlayerNavigation(
            modifier = Modifier.fillMaxSize(),
            navController = navController
        )
        BottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            current = currentRoute,
            onClick = { route ->
                navController.navigate(route.path) {
                    launchSingleTop = true
                }
            }
        )
    }
}
