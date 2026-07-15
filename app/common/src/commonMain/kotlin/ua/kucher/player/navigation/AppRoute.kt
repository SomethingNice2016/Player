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
import ua.kucher.player.core.common.uuid.uuid


internal sealed interface AppRoute : NavKey {

    val id: String

    companion object {
        val mainMenuItems = listOf(
            { Home() },
            { AllSong() },
            { Settings() }
        )
    }

    @Serializable
    data class Home(
        override val id: String = uuid()
    ) : AppRoute

    @Serializable
    data class AllSong(
        override val id: String = uuid()
    ) : AppRoute

    @Serializable
    data class Settings(
        override val id: String = uuid()
    ) : AppRoute

    @Serializable
    data class SongsSearch(
        override val id: String = uuid()
    ) : AppRoute

    @Serializable
    data class ArtistList(
        override val id: String = uuid()
    ) : AppRoute

    @Serializable
    data class ArtistSearch(
        override val id: String = uuid()
    ) : AppRoute

    @Serializable
    data class AlbumsList(
        override val id: String = uuid()
    ) : AppRoute

    @Serializable
    data class AlbumSearch(
        override val id: String = uuid()
    ) : AppRoute

    @Serializable
    data class FavoriteSongs(
        override val id: String = uuid()
    ) : AppRoute

    @Serializable
    data class SongMenu(
        override val id: String = uuid()
    ) : AppRoute
}

internal val AppRoute.label: StringResource?
    get() = when (this) {
        is AppRoute.Home -> Res.string.home_label
        is AppRoute.AllSong -> Res.string.music_label
        is AppRoute.Settings -> Res.string.setting_label
        else -> null
    }

internal val AppRoute.icon: DrawableResource?
    get() = when (this) {
        is AppRoute.Home -> Res.drawable.ic_home
        is AppRoute.AllSong -> Res.drawable.ic_music
        is AppRoute.Settings -> Res.drawable.ic_setting
        else -> null
    }