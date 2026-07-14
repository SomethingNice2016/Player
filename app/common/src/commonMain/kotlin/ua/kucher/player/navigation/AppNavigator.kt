package ua.kucher.player.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList


@Composable
internal fun rememberAppNavigator() = remember {
    AppNavigator()
}

internal class AppNavigator {

    val backStack: List<AppRoute>
        field: SnapshotStateList<AppRoute> = mutableStateListOf(AppRoute.Home)

    val current: AppRoute
        get() = backStack.lastOrNull() ?: AppRoute.Home

    fun navigateBack() {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }

    fun navigateToAllSongs() {
        navigate(AppRoute.AllSong)
    }

    fun navigateToSongSearch() {
        navigate(AppRoute.SongsSearch)
    }

    fun navigateToFavoriteSongs() {
        navigate(AppRoute.FavoriteSongs)
    }

    fun navigateToArtistList() {
        navigate(AppRoute.ArtistList)
    }

    fun navigateToArtistSearch() {
        navigate(AppRoute.ArtistSearch)
    }

    fun navigateToAlbumList() {
        navigate(AppRoute.AlbumsList)
    }

    fun navigateToAlbumSearch() {
        navigate(AppRoute.AlbumSearch)
    }

    fun navigate(route: AppRoute) {
        backStack.add(route)
    }

}