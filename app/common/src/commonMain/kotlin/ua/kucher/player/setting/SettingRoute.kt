package ua.kucher.player.setting

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.github_url
import ua.kucher.player.navigation.AppRouter
import ua.kucher.player.openUrl

@Composable
internal fun SettingRoute(
    router: AppRouter,
    presenter: SettingPresenter
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