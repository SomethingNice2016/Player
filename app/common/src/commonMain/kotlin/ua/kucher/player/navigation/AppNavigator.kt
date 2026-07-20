package ua.kucher.player.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList

@Composable
internal fun rememberAppNavigator(startEntry: AppEntry) = remember {
    AppNavigator(startEntry)
}

internal class AppNavigator(
    startEntry: AppEntry
) {

    private val _backStack: SnapshotStateList<AppEntry> =
        mutableStateListOf()

    val backStack: List<AppEntry>
        get() = _backStack

    val currentEntry: AppEntry
        get() = _backStack.last()

    val currentMenuEntry: AppEntry
        get() = requireNotNull(
            value = _backStack.findLast { route ->
                AppEntry.mainMenuItemsClass.contains(route::class)
            }
        )

    init {
        navigate(startEntry)
    }

    fun navigate(route: AppEntry) {
        _backStack.add(route)
    }

    fun navigateBack() {
        if (_backStack.size <= 1) return
        removeLastEntry()
    }

    fun navigateToAllSongs() =
        navigate(AppEntry.AllSong())

    fun navigateToSongSearch() =
        navigate(AppEntry.SongsSearch())

    fun navigateToFavoriteSongs() =
        navigate(AppEntry.FavoriteSongs())

    fun navigateToArtistList() =
        navigate(AppEntry.ArtistList())

    fun navigateToArtistSearch() =
        navigate(AppEntry.ArtistSearch())

    fun navigateToAlbumList() =
        navigate(AppEntry.AlbumsList())

    fun navigateToAlbumSearch() =
        navigate(AppEntry.AlbumSearch())

    fun navigateToAlbum(id: Long) {

    }

    fun navigateToArtist(id: Long) {

    }

    private fun removeEntry(index: Int) {
        _backStack.removeAt(index).clear()
    }

    private fun removeLastEntry() {
        _backStack.removeLast().clear()
    }
}