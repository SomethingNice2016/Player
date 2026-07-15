package ua.kucher.player.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import ua.kucher.player.album.list.AlbumListRoute
import ua.kucher.player.album.search.AlbumSearchRoute
import ua.kucher.player.artist.list.ArtistListRoute
import ua.kucher.player.artist.search.ArtistSearchRoute
import ua.kucher.player.core.common.backhandler.PlatformBackHandler
import ua.kucher.player.home.HomeRoute
import ua.kucher.player.setting.SettingRoute
import ua.kucher.player.song.allsongs.AllSongRoute
import ua.kucher.player.song.favorite.FavoriteSongRoute
import ua.kucher.player.song.search.SongsSearchRoute
import ua.kucher.player.theme.extensions.koinPresenter

@Composable
internal fun PlayerNavigation(
    modifier: Modifier = Modifier,
    navigator: AppNavigator,
    playerExpanded: Boolean,
    collapsePlayer: () -> Unit
) {
    NavDisplay(
        modifier = modifier,
        onBack = { navigator.navigateBack() },
        backStack = navigator.backStack,
        entryProvider = entryProvider {
            entry<AppRoute.Home> { route ->
                HomeRoute(
                    navigator = navigator,
                    presenter = koinPresenter(navigator.getEntry(route))
                )
            }

            entry<AppRoute.AllSong> { route ->
                AllSongRoute(
                    navigator = navigator,
                    presenter = koinPresenter(navigator.getEntry(route))
                )
            }

            entry<AppRoute.FavoriteSongs> { route ->
                FavoriteSongRoute(
                    navigator = navigator,
                    presenter = koinPresenter(navigator.getEntry(route))
                )
            }

            entry<AppRoute.Settings> { route ->
                SettingRoute(
                    navigator = navigator,
                    presenter = koinPresenter(navigator.getEntry(route))
                )
            }

            entry<AppRoute.SongsSearch> { route ->
                SongsSearchRoute(
                    navigator = navigator,
                    presenter = koinPresenter(navigator.getEntry(route))
                )
            }

            entry<AppRoute.ArtistList> { route ->
                ArtistListRoute(
                    navigator = navigator,
                    presenter = koinPresenter(navigator.getEntry(route))
                )
            }

            entry<AppRoute.ArtistSearch> { route ->
                ArtistSearchRoute(
                    navigator = navigator,
                    presenter = koinPresenter(navigator.getEntry(route))
                )
            }

            entry<AppRoute.AlbumsList> { route ->
                AlbumListRoute(
                    navigator = navigator,
                    presenter = koinPresenter(navigator.getEntry(route))
                )
            }

            entry<AppRoute.AlbumSearch> { route ->
                AlbumSearchRoute(
                    navigator = navigator,
                    presenter = koinPresenter(navigator.getEntry(route))
                )
            }
        }
    )
    PlatformBackHandler(playerExpanded) {
        collapsePlayer()
    }
}