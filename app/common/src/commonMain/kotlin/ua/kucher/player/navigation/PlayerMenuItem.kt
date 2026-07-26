package ua.kucher.player.navigation

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.home_label
import player.app.common.generated.resources.ic_home
import player.app.common.generated.resources.ic_music
import player.app.common.generated.resources.ic_setting
import player.app.common.generated.resources.music_label
import player.app.common.generated.resources.setting_label
import ua.kucher.player.theme.components.bottombar.MenuItem
import kotlin.reflect.KClass

@Immutable
internal data class PlayerMenuItem(
    val screen: KClass<out AppRoute>,
    override val label: StringResource,
    override val icon: DrawableResource,
    val create: () -> AppRoute
) : MenuItem

internal val menuItems = listOf(
    PlayerMenuItem(
        screen = AppRoute.Home::class,
        label = Res.string.home_label,
        icon = Res.drawable.ic_home,
        create = AppRoute::Home
    ),
    PlayerMenuItem(
        screen = AppRoute.AllSong::class,
        label = Res.string.music_label,
        icon = Res.drawable.ic_music,
        create = AppRoute::AllSong
    ),
    PlayerMenuItem(
        screen = AppRoute.Settings::class,
        label = Res.string.setting_label,
        icon = Res.drawable.ic_setting,
        create = AppRoute::Settings
    ),
)
