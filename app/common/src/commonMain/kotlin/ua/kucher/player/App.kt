package ua.kucher.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ua.kucher.player.navigation.AppNavigator
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

    val playerPresenter: MusicPlayerPresenter = koinPresenter()

    val navigator: AppNavigator = rememberAppNavigator()

    PlayerTheme(useDarkTheme = true) {

        val playerPadding = rememberMiniPlayerPadding()

        var playerExpanded by remember {
            mutableStateOf(false)
        }

        MusicPlayerRoute(
            modifier = Modifier.fillMaxSize(),
            presenter = playerPresenter,
            navigator = navigator,
            onPlayerExpanded = { expanded ->
                playerExpanded = expanded
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                PlayerNavigation(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = playerPadding),
                    navigator = navigator,
                    playerExpanded = playerExpanded,
                    collapsePlayer = playerPresenter::collapsePlayer
                )

                BottomBar(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    current = navigator.currentRoute,
                    items = AppRoute.mainMenuItems,
                    onClick = navigator::navigate
                )
            }
        }
    }
}

