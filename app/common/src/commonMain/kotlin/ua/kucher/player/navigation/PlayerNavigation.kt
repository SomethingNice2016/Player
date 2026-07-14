package ua.kucher.player.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import ua.kucher.player.album.list.AlbumListRoute
import ua.kucher.player.album.search.AlbumSearchRoute
import ua.kucher.player.artist.list.ArtistListRoute
import ua.kucher.player.artist.search.ArtistSearchRoute
import ua.kucher.player.home.HomeRoute
import ua.kucher.player.setting.SettingRoute
import ua.kucher.player.song.list.SongListRoute
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
        startDestination = startDestination.path,
    ) {
        composable(route = AppRoute.Home.path) { navBackStackEntry ->
            HomeRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }

        composable(route = AppRoute.SongList.path) { navBackStackEntry ->
            SongListRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }

        composable(route = AppRoute.Settings.path) { navBackStackEntry ->
            SettingRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }

        composable(route = AppRoute.SongsSearch.path) { navBackStackEntry ->
            SongsSearchRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }

        composable(route = AppRoute.ArtistList.path) { navBackStackEntry ->
            ArtistListRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }

        composable(route = AppRoute.ArtistSearch.path) { navBackStackEntry ->
            ArtistSearchRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }

        composable(route = AppRoute.AlbumList.path) { navBackStackEntry ->
            AlbumListRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }

        composable(route = AppRoute.AlbumSearch.path) { navBackStackEntry ->
            AlbumSearchRoute(
                navController = navController,
                viewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
            )
        }
    }
}