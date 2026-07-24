package ua.kucher.player.core.ui.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember

@Composable
inline fun <reified T : Presenter> rememberPresenter(
    owner: PresenterStoreOwner = requireNotNull(LocalPresenterStoreOwner.current) {
        "Presenter owner must not be null!"
    },
    crossinline factory: () -> T,
): T {
    return remember(owner) {
        owner.presenterStore.getOrPut(T::class) {
            factory()
        }
    }
}

@Composable
inline fun <reified T : Presenter> rememberLocalPresenter(
    crossinline factory: () -> T,
): T {

    val localPresenterStore = remember {
        PresenterStore()
    }

    val presenter = remember(localPresenterStore) {
        localPresenterStore.getOrPut(T::class) {
            factory()
        }
    }

    DisposableEffect(presenter) {
        onDispose {
            localPresenterStore.clear()
        }
    }

    return presenter
}