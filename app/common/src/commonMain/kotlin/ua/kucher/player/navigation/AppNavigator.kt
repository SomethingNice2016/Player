package ua.kucher.player.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import co.touchlab.kermit.Logger

@Composable
internal fun rememberAppNavigator() = remember {
    AppNavigator()
}

internal class AppNavigator {

    private val entries = mutableMapOf<String, ScreenEntry<AppRoute>>()
    val backStack: List<AppRoute>
        field: SnapshotStateList<AppRoute> = mutableStateListOf()

    val currentRoute: AppRoute
        get() = backStack.last()

    val currentEntry: ScreenEntry<AppRoute>
        get() = requireNotNull(entries[currentRoute.id])

    init {
        navigate(AppRoute.Home())
    }

    private fun logBackstack() {
        backStack.forEach {
            Logger.d(
                messageString = "route: ${it::class.simpleName} id: ${it.id}",
                tag = AppNavigator::class.simpleName.toString()
            )
        }
    }

    fun getEntry(route: AppRoute): ScreenEntry<AppRoute> =
        requireNotNull(entries[route.id]) {
            "ScreenEntry for ${route::class.simpleName} (${route.id}) not found"
        }

    fun navigate(route: AppRoute) {
        val entry = ScreenEntry(route)
        backStack.add(route)
        entries[route.id] = entry
        logBackstack()
    }

    fun navigateBack() {
        if (backStack.size <= 1) return
        backStack.lastOrNull()?.let { route ->
            backStack.removeLastOrNull()
            entries.remove(route.id)?.clear()
        }
        logBackstack()
    }

    fun navigateToAllSongs() =
        navigate(AppRoute.AllSong())

    fun navigateToSongSearch() =
        navigate(AppRoute.SongsSearch())

    fun navigateToFavoriteSongs() =
        navigate(AppRoute.FavoriteSongs())

    fun navigateToArtistList() =
        navigate(AppRoute.ArtistList())

    fun navigateToArtistSearch() =
        navigate(AppRoute.ArtistSearch())

    fun navigateToAlbumList() =
        navigate(AppRoute.AlbumsList())

    fun navigateToAlbumSearch() =
        navigate(AppRoute.AlbumSearch())
}