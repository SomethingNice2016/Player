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


internal sealed class AppEntry : ScreenEntry(), NavKey {

    companion object {
        val mainMenuItemsFactories = listOf(
            { Home() },
            { AllSong() },
            { Settings() }
        )

        val mainMenuItemsClass = setOf(
            Home::class,
            AllSong::class,
            Settings::class
        )
    }

    @Serializable
    data class Home(
        override val id: String = uuid()
    ) : AppEntry()

    @Serializable
    data class AllSong(
        override val id: String = uuid()
    ) : AppEntry()

    @Serializable
    data class Settings(
        override val id: String = uuid()
    ) : AppEntry()

    @Serializable
    data class SongsSearch(
        override val id: String = uuid()
    ) : AppEntry()

    @Serializable
    data class ArtistList(
        override val id: String = uuid()
    ) : AppEntry()

    @Serializable
    data class ArtistSearch(
        override val id: String = uuid()
    ) : AppEntry()

    @Serializable
    data class AlbumsList(
        override val id: String = uuid()
    ) : AppEntry()

    @Serializable
    data class AlbumSearch(
        override val id: String = uuid()
    ) : AppEntry()

    @Serializable
    data class FavoriteSongs(
        override val id: String = uuid()
    ) : AppEntry()

}

internal val AppEntry.label: StringResource?
    get() = when (this) {
        is AppEntry.Home -> Res.string.home_label
        is AppEntry.AllSong -> Res.string.music_label
        is AppEntry.Settings -> Res.string.setting_label
        else -> null
    }

internal val AppEntry.icon: DrawableResource?
    get() = when (this) {
        is AppEntry.Home -> Res.drawable.ic_home
        is AppEntry.AllSong -> Res.drawable.ic_music
        is AppEntry.Settings -> Res.drawable.ic_setting
        else -> null
    }