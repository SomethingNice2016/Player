package ua.kucher.player.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import ua.kucher.player.album.list.AlbumListRoute
import ua.kucher.player.album.search.AlbumSearchRoute
import ua.kucher.player.artist.list.ArtistListRoute
import ua.kucher.player.artist.search.ArtistSearchRoute
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
) {
    NavDisplay(
        modifier = modifier,
        onBack = { navigator.navigateBack() },
        backStack = navigator.backStack,
        entryProvider = entryProvider {

            entry<AppRoute.Home> {
                HomeRoute(
                    navigator = navigator,
                    presenter = koinPresenter()
                )
            }

            entry<AppRoute.AllSong> {
                AllSongRoute(
                    navigator = navigator,
                    presenter = koinPresenter()
                )
            }

            entry<AppRoute.FavoriteSongs> {
                FavoriteSongRoute(
                    navigator = navigator,
                    presenter = koinPresenter()
                )
            }

            entry<AppRoute.Settings> {
                SettingRoute(
                    navigator = navigator,
                    presenter = koinPresenter()
                )
            }

            entry<AppRoute.SongsSearch> {
                SongsSearchRoute(
                    navigator = navigator,
                    presenter = koinPresenter()
                )
            }

            entry<AppRoute.ArtistList> {
                ArtistListRoute(
                    navigator = navigator,
                    presenter = koinPresenter()
                )
            }

            entry<AppRoute.ArtistSearch> {
                ArtistSearchRoute(
                    navigator = navigator,
                    presenter = koinPresenter()
                )
            }

            entry<AppRoute.AlbumsList> {
                AlbumListRoute(
                    navigator = navigator,
                    presenter = koinPresenter()
                )
            }

            entry<AppRoute.AlbumSearch> {
                AlbumSearchRoute(
                    navigator = navigator,
                    presenter = koinPresenter()
                )
            }
        }
    )
}