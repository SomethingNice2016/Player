package ua.kucher.player.theme.extensions

import androidx.compose.runtime.Composable
import org.koin.core.parameter.ParametersHolder
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform.getKoin
import ua.kucher.player.core.ui.presenter.LocalPresenterStoreOwner
import ua.kucher.player.core.ui.presenter.Presenter
import ua.kucher.player.core.ui.presenter.PresenterStoreOwner
import ua.kucher.player.core.ui.presenter.rememberLocalPresenter
import ua.kucher.player.core.ui.presenter.rememberPresenter

@Composable
internal inline fun <reified T : Presenter> koinLocalPresenter(
    crossinline parameters: (() -> ParametersHolder) = { parametersOf() },
): T {
    return rememberLocalPresenter {
        resolvePresenter(parameters)
    }
}


@Composable
internal inline fun <reified T : Presenter> koinPresenter(
    owner: PresenterStoreOwner = requireNotNull(LocalPresenterStoreOwner.current) {
        "Presenter owner must not be null!"
    },
    crossinline parameters: (() -> ParametersHolder) = { parametersOf() },
): T {
    return rememberPresenter(owner) {
        resolvePresenter(parameters)
    }
}

private inline fun <reified T : Presenter> resolvePresenter(
    parameters: () -> ParametersHolder
): T {
    val userParameters = parameters().values
    return getKoin().get {
        parametersOf(*userParameters.toTypedArray())
    }
}