package ua.kucher.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ua.kucher.player.navigation.AppRoute
import ua.kucher.player.navigation.PlayerNavigation
import ua.kucher.player.navigation.rememberAppNavigator
import ua.kucher.player.songplayer.MusicPlayerPresenter
import ua.kucher.player.songplayer.MusicPlayerRoute
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.BottomBar
import ua.kucher.player.theme.extensions.koinPresenter
import ua.kucher.player.theme.extensions.rememberMiniPlayerPadding

@Composable
fun App() {

    val playerViewModel: MusicPlayerPresenter = koinPresenter()

    PlayerTheme(useDarkTheme = true) {

        val playerPadding = rememberMiniPlayerPadding()

        val navigator = rememberAppNavigator()

        MusicPlayerRoute(
            modifier = Modifier.fillMaxSize(),
            viewModel = playerViewModel
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                PlayerNavigation(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = playerPadding),
                    navigator = navigator
                )

                BottomBar(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    current = navigator.current,
                    items = AppRoute.mainMenuItems,
                    onClick = navigator::navigate
                )
            }
        }
    }
}

