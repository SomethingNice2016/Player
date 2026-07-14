package ua.kucher.player.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.home_label
import player.app.common.generated.resources.ic_home
import player.app.common.generated.resources.ic_music
import player.app.common.generated.resources.ic_setting
import player.app.common.generated.resources.music_label
import player.app.common.generated.resources.setting_label


internal sealed interface AppRoute : NavKey {

    companion object {
        val mainMenuItems = listOf<AppRoute>(
            Home, AllSong, Settings
        )
    }

    @Serializable
    data object Home : AppRoute

    @Serializable
    data object AllSong : AppRoute

    @Serializable
    data object Settings : AppRoute

    @Serializable
    data object SongsSearch : AppRoute

    @Serializable
    data object ArtistList : AppRoute

    @Serializable
    data object ArtistSearch : AppRoute

    @Serializable
    data object AlbumList : AppRoute

    @Serializable
    data object AlbumSearch : AppRoute

    @Serializable
    data object FavoriteSongs : AppRoute

    @Serializable
    data object SongMenu : AppRoute
}

internal val AppRoute.label: StringResource?
    get() = when (this) {
        AppRoute.Home -> Res.string.home_label
        AppRoute.AllSong -> Res.string.music_label
        AppRoute.Settings -> Res.string.setting_label
        else -> null
    }

internal val AppRoute.icon: DrawableResource?
    get() = when (this) {
        AppRoute.Home -> Res.drawable.ic_home
        AppRoute.AllSong -> Res.drawable.ic_music
        AppRoute.Settings -> Res.drawable.ic_setting
        else -> null
    }