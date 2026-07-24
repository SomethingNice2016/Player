package ua.kucher.player.theme.components.bottombar

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

internal interface MenuItem {
    val label: StringResource
    val icon: DrawableResource
}