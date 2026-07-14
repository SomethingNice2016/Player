package ua.kucher.player.navigation

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.home_label
import player.app.common.generated.resources.ic_home
import player.app.common.generated.resources.ic_music
import player.app.common.generated.resources.ic_setting
import player.app.common.generated.resources.music_label
import player.app.common.generated.resources.setting_label

internal sealed class AppRoute(
    val path: String,
    val label: StringResource? = null,
    val icon: DrawableResource? = null
) {

    companion object {
        fun getMainMenuItems() = listOf(
            Home, AllSong, Settings
        )

        fun values() = listOf(
            Home,
            AllSong,
            Settings,
            SongsSearch,
            ArtistList,
            ArtistSearch,
            AlbumList,
            AlbumSearch,
            FavoriteSongs
        )

        fun getByPath(path: String) = values().find { value ->
            value.path == path
        } ?: throw IllegalArgumentException("Incorrect path!")
    }

    data object Home : AppRoute(
        path = "home",
        label = Res.string.home_label,
        icon = Res.drawable.ic_home
    )

    data object AllSong : AppRoute(
        path = "all_songs",
        label = Res.string.music_label,
        icon = Res.drawable.ic_music
    )

    data object Settings : AppRoute(
        path = "settings",
        label = Res.string.setting_label,
        icon = Res.drawable.ic_setting
    )

    data object SongsSearch : AppRoute(
        path = "songs_search",
        label = null,
        icon = null
    )

    data object ArtistList: AppRoute(
        path = "artists",
        label = null,
        icon = null
    )

    data object ArtistSearch: AppRoute(
        path = "artists_search",
        label = null,
        icon = null
    )

    data object AlbumList: AppRoute(
        path = "album_list",
        label = null,
        icon = null
    )

    data object AlbumSearch: AppRoute(
        path = "album_search",
        label = null,
        icon = null
    )

    data object FavoriteSongs: AppRoute(
        path = "favorite_songs",
        label = null,
        icon = null
    )
}