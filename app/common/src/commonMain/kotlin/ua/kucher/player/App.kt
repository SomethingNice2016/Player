package ua.kucher.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ua.kucher.player.core.ui.backhandler.PlatformBackHandler
import ua.kucher.player.navigation.AppRoute
import ua.kucher.player.navigation.AppRouter
import ua.kucher.player.navigation.PlayerNavDisplay
import ua.kucher.player.navigation.menuItems
import ua.kucher.player.navigation.rememberRouter
import ua.kucher.player.songplayer.MusicPlayerPresenter
import ua.kucher.player.songplayer.MusicPlayerRoute
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.bottombar.BottomBar
import ua.kucher.player.theme.extensions.koinPresenter
import ua.kucher.player.theme.extensions.rememberMiniPlayerPadding

@Composable
fun App() {

    val playerPresenter: MusicPlayerPresenter = koinPresenter()

    val router: AppRouter = rememberRouter {
        AppRoute.Home()
    }

    PlayerTheme(useDarkTheme = true) {

        var playerExpanded by remember {
            mutableStateOf(false)
        }

        PlatformBackHandler(!playerExpanded) {
            router.navigateBack()
        }

        val playerPadding = rememberMiniPlayerPadding()

        LaunchedEffect(router.currentRoute) {
            if (playerExpanded) {
                playerPresenter.collapsePlayer()
            }
        }

        MusicPlayerRoute(
            modifier = Modifier.fillMaxSize(),
            presenter = playerPresenter,
            router = router,
            onPlayerExpanded = { expanded ->
                playerExpanded = expanded
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                PlayerNavDisplay(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = playerPadding),
                    router = router,
                )

                BottomBar(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    items = menuItems,
                    onClick = { item ->
                        if (item.screen == router.currentRoute::class) return@BottomBar
                        router.reset(item.create())
                    },
                    isSelected = { item ->
                        router.backStack.first()::class == item.screen
                    }
                )
            }
        }
        PlatformBackHandler(playerExpanded) {
            playerPresenter.collapsePlayer()
        }
    }
}

