package ua.kucher.player.navigation

import androidx.navigation.NavController


internal fun NavController.navigateTo(route: AppRoute) {
    navigate(route.path)
}