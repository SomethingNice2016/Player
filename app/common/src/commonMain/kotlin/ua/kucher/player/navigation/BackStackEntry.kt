package ua.kucher.player.navigation

import ua.kucher.player.core.ui.presenter.PresenterStore
import ua.kucher.player.core.ui.presenter.PresenterStoreOwner

internal class BackStackEntry<T : Route>(
    val route: T
) : PresenterStoreOwner,
    AutoCloseable {

    override val presenterStore = PresenterStore()

    override fun close() {
        presenterStore.clear()
    }
}