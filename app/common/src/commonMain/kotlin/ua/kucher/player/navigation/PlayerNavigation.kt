package ua.kucher.player.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import ua.kucher.player.album.list.AlbumListRoute
import ua.kucher.player.album.search.AlbumSearchRoute
import ua.kucher.player.artist.list.ArtistListRoute
import ua.kucher.player.artist.search.ArtistSearchRoute
import ua.kucher.player.home.HomeRoute
import ua.kucher.player.setting.SettingRoute
import ua.kucher.player.song.allsongs.AllSongRoute
import ua.kucher.player.song.favorite.FavoriteSongRoute
import ua.kucher.player.song.menu.SongMenuRoute
import ua.kucher.player.song.search.SongsSearchRoute

@Composable
internal fun PlayerNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: AppRoute = AppRoute.Home,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<AppRoute.Home> { navBackStackEntry ->
            HomeRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }

        composable<AppRoute.AllSong> { navBackStackEntry ->
            AllSongRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }

        composable<AppRoute.FavoriteSongs> { navBackStackEntry ->
            FavoriteSongRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }

        composable<AppRoute.Settings> { navBackStackEntry ->
            SettingRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }

        composable<AppRoute.SongsSearch> { navBackStackEntry ->
            SongsSearchRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }

        composable<AppRoute.ArtistList> { navBackStackEntry ->
            ArtistListRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }

        composable<AppRoute.ArtistSearch> { navBackStackEntry ->
            ArtistSearchRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }

        composable<AppRoute.AlbumList> { navBackStackEntry ->
            AlbumListRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }

        composable<AppRoute.AlbumSearch> { navBackStackEntry ->
            AlbumSearchRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }

        dialog<AppRoute.SongMenu>(
            dialogProperties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false
            ),
        ) { navBackStackEntry ->
            SongMenuRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }
    }
}