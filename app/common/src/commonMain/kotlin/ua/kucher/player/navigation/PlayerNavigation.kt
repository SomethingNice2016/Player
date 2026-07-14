package ua.kucher.player.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import org.koin.compose.viewmodel.koinViewModel
import ua.kucher.player.album.list.AlbumListRoute
import ua.kucher.player.album.search.AlbumSearchRoute
import ua.kucher.player.artist.list.ArtistListRoute
import ua.kucher.player.artist.search.ArtistSearchRoute
import ua.kucher.player.home.HomeRoute
import ua.kucher.player.setting.SettingRoute
import ua.kucher.player.song.allsongs.AllSongRoute
import ua.kucher.player.song.favorite.FavoriteSongRoute
import ua.kucher.player.song.search.SongsSearchRoute

internal typealias AppBackStack = SnapshotStateList<AppRoute>

@Composable
internal fun PlayerNavigation(
    modifier: Modifier = Modifier,
    backStack: AppBackStack,
) {
    NavDisplay(
        modifier = modifier,
        onBack = { backStack.removeLastOrNull() },
        backStack = backStack,
        entryProvider = entryProvider {

            entry<AppRoute.Home> {
                HomeRoute(
                    backStack = backStack,
                    viewModel = koinViewModel()
                )
            }

            entry<AppRoute.AllSong> {
                AllSongRoute(
                    backStack = backStack,
                    viewModel = koinViewModel()
                )
            }

            entry<AppRoute.FavoriteSongs> {
                FavoriteSongRoute(
                    backStack = backStack,
                    viewModel = koinViewModel()
                )
            }

            entry<AppRoute.Settings> {
                SettingRoute(
                    backStack = backStack,
                    viewModel = koinViewModel()
                )
            }

            entry<AppRoute.SongsSearch> {
                SongsSearchRoute(
                    backStack = backStack,
                    viewModel = koinViewModel()
                )
            }

            entry<AppRoute.ArtistList> {
                ArtistListRoute(
                    backStack = backStack,
                    viewModel = koinViewModel()
                )
            }

            entry<AppRoute.ArtistSearch> {
                ArtistSearchRoute(
                    backStack = backStack,
                    viewModel = koinViewModel()
                )
            }

            entry<AppRoute.AlbumList> {
                AlbumListRoute(
                    backStack = backStack,
                    viewModel = koinViewModel()
                )
            }

            entry<AppRoute.AlbumSearch> {
                AlbumSearchRoute(
                    backStack = backStack,
                    viewModel = koinViewModel()
                )
            }
        }
    )
}