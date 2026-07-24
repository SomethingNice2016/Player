package ua.kucher.player.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import org.koin.core.parameter.parametersOf
import ua.kucher.player.album.detail.AlbumDetailRoute
import ua.kucher.player.album.list.AlbumListRoute
import ua.kucher.player.album.search.AlbumSearchRoute
import ua.kucher.player.artist.detail.ArtistDetailRoute
import ua.kucher.player.artist.list.ArtistListRoute
import ua.kucher.player.artist.search.ArtistSearchRoute
import ua.kucher.player.home.HomeRoute
import ua.kucher.player.setting.SettingRoute
import ua.kucher.player.song.allsongs.AllSongRoute
import ua.kucher.player.song.favorite.FavoriteSongRoute
import ua.kucher.player.song.search.SongsSearchRoute
import ua.kucher.player.theme.extensions.koinPresenter

@Composable
internal fun PlayerNavDisplay(
    modifier: Modifier = Modifier,
    router: AppRouter,
) {

    NavDisplay(
        modifier = modifier,
        onBack = router::navigateBack,
        backStack = router.backStack,
        entryProvider = entryProvider {

            entry<AppRoute.Home> { route ->
                HomeRoute(
                    router = router,
                    presenter = koinPresenter(router.getEntry(route))
                )
            }

            entry<AppRoute.AllSong> { route ->
                AllSongRoute(
                    router = router,
                    presenter = koinPresenter(router.getEntry(route))
                )
            }

            entry<AppRoute.FavoriteSongs> { route ->
                FavoriteSongRoute(
                    router = router,
                    presenter = koinPresenter(router.getEntry(route))
                )
            }

            entry<AppRoute.Settings> { route ->
                SettingRoute(
                    router = router,
                    presenter = koinPresenter(router.getEntry(route))
                )
            }

            entry<AppRoute.SongsSearch> { route ->
                SongsSearchRoute(
                    router = router,
                    presenter = koinPresenter(router.getEntry(route))
                )
            }

            entry<AppRoute.ArtistList> { route ->
                ArtistListRoute(
                    router = router,
                    presenter = koinPresenter(router.getEntry(route))
                )
            }

            entry<AppRoute.ArtistSearch> { route ->
                ArtistSearchRoute(
                    router = router,
                    presenter = koinPresenter(router.getEntry(route))
                )
            }

            entry<AppRoute.AlbumsList> { route ->
                AlbumListRoute(
                    navigator = router,
                    presenter = koinPresenter(router.getEntry(route))
                )
            }

            entry<AppRoute.AlbumSearch> { route ->
                AlbumSearchRoute(
                    router = router,
                    presenter = koinPresenter(router.getEntry(route))
                )
            }

            entry<AppRoute.Album> { route ->
                AlbumDetailRoute(
                    router = router,
                    presenter = koinPresenter(router.getEntry(route)) {
                        parametersOf(route.albumId)
                    }
                )
            }

            entry<AppRoute.Artist> { route ->
                ArtistDetailRoute(
                    router = router,
                    presenter = koinPresenter(router.getEntry(route)) {
                        parametersOf(route.artistId)
                    }
                )
            }
        }
    )
}