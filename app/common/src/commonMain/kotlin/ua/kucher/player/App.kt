package ua.kucher.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

    val backStack = remember {
        mutableStateListOf<AppRoute>(AppRoute.Home)
    }

    val playerViewModel: MusicPlayerViewModel = koinViewModel()

    val playerPadding = rememberMiniPlayerPadding()

    val currentRoute = backStack.lastOrNull() ?: AppRoute.Home

    MusicPlayerRoute(
        modifier = Modifier.fillMaxSize(),
        viewModel = playerViewModel
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            PlayerNavigation(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = playerPadding),
                backStack = backStack
            )

            BottomBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                current = currentRoute,
                items = AppRoute.mainMenuItems,
                onClick = backStack::add
            )
        }
    }
}

