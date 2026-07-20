package ua.kucher.player.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import ua.kucher.player.album.list.AlbumListRoute
import ua.kucher.player.album.search.AlbumSearchRoute
import ua.kucher.player.artist.list.ArtistListRoute
import ua.kucher.player.artist.search.ArtistSearchRoute
import ua.kucher.player.core.ui.backhandler.PlatformBackHandler
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

            entry<AppEntry.Home> { entry ->
                HomeRoute(
                    navigator = navigator,
                    presenter = koinPresenter(entry)
                )
            }

            entry<AppEntry.AllSong> { entry ->
                AllSongRoute(
                    navigator = navigator,
                    presenter = koinPresenter(entry)
                )
            }

            entry<AppEntry.FavoriteSongs> { entry ->
                FavoriteSongRoute(
                    navigator = navigator,
                    presenter = koinPresenter(entry)
                )
            }

            entry<AppEntry.Settings> { entry ->
                SettingRoute(
                    navigator = navigator,
                    presenter = koinPresenter(entry)
                )
            }

            entry<AppEntry.SongsSearch> { entry ->
                SongsSearchRoute(
                    navigator = navigator,
                    presenter = koinPresenter(entry)
                )
            }

            entry<AppEntry.ArtistList> { entry ->
                ArtistListRoute(
                    navigator = navigator,
                    presenter = koinPresenter(entry)
                )
            }

            entry<AppEntry.ArtistSearch> { entry ->
                ArtistSearchRoute(
                    navigator = navigator,
                    presenter = koinPresenter(entry)
                )
            }

            entry<AppEntry.AlbumsList> { entry ->
                AlbumListRoute(
                    navigator = navigator,
                    presenter = koinPresenter(entry)
                )
            }

            entry<AppEntry.AlbumSearch> { entry ->
                AlbumSearchRoute(
                    navigator = navigator,
                    presenter = koinPresenter(entry)
                )
            }
        }
    )
    PlatformBackHandler(playerExpanded) {
        collapsePlayer()
    }
}