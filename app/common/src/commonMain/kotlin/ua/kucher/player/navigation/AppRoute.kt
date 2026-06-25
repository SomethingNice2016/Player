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
            Home, SongList, Settings
        )

        fun values() = listOf(
            Home,
            SongList,
            Settings,
            Search
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

    data object SongList : AppRoute(
        path = "songs",
        label = Res.string.music_label,
        icon = Res.drawable.ic_music
    )

    data object Settings : AppRoute(
        path = "settings",
        label = Res.string.setting_label,
        icon = Res.drawable.ic_setting
    )

    data object Search : AppRoute(
        path = "search",
        label = null,
        icon = null
    )
}