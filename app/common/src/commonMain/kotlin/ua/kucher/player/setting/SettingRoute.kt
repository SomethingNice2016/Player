package ua.kucher.player.setting

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.jetbrains.compose.resources.stringResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.github_url
import ua.kucher.player.openUrl

@Composable
internal fun SettingRoute(
    navController: NavController,
    viewModel: SettingViewModel
) {
    val githubUrl = stringResource(Res.string.github_url)

    SettingScreen(
        onRateAppClick = {
            println("Rate app click")
        },
        onSourceCodeClick = {
            openUrl(githubUrl)
        },
        onAboutClick = {
            println("About click")
        }
    )
}