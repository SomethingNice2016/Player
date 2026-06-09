package ua.kucher.player.setting

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
internal fun SettingRoute(
    navController: NavController,
    viewModel: SettingViewModel
) {
    SettingScreen()
}