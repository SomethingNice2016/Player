package ua.kucher.player.core.ui.presenter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class Presenter {

    protected val scope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main.immediate
    )

    fun dispose() {
        scope.cancel()
    }
}