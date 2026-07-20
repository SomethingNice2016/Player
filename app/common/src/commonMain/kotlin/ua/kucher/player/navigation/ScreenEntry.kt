package ua.kucher.player.navigation

import ua.kucher.player.core.ui.presenter.PresenterStore
import ua.kucher.player.core.ui.presenter.PresenterStoreOwner

internal abstract class ScreenEntry : PresenterStoreOwner {

    override val presenterStore: PresenterStore = PresenterStore()

    abstract val id: String

    fun clear() {
        presenterStore.clear()
    }

}