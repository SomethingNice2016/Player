package ua.kucher.player.theme.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import org.koin.mp.KoinPlatform.getKoin
import ua.kucher.player.core.ui.presenter.Presenter

@Composable
inline fun <reified T : Presenter> koinPresenter(): T {

    val presenter = remember {
        getKoin().get<T>()
    }

    DisposableEffect(presenter) {
        onDispose {
            presenter.dispose()
        }
    }

    return presenter
}