package ua.kucher.player.theme.extensions

import androidx.compose.runtime.Composable
import org.koin.core.parameter.ParametersHolder
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform.getKoin
import ua.kucher.player.core.ui.presenter.Presenter
import ua.kucher.player.core.ui.presenter.PresenterStoreOwner
import ua.kucher.player.core.ui.presenter.rememberPresenter

@Composable
internal inline fun <reified T : Presenter> koinPresenter(
    crossinline parameters: (() -> ParametersHolder) = { parametersOf() },
): T {
    return rememberPresenter {
        resolvePresenter(parameters)
    }
}


@Composable
internal inline fun <reified T : Presenter> koinPresenter(
    owner: PresenterStoreOwner,
    crossinline parameters: (() -> ParametersHolder) = { parametersOf() },
): T {
    return rememberPresenter(owner) {
        resolvePresenter(parameters)
    }
}

private inline fun <reified T : Presenter> resolvePresenter(
    crossinline parameters: () -> ParametersHolder
): T {
    val userParameters = parameters().values
    return getKoin().get {
        parametersOf(*userParameters.toTypedArray())
    }
}