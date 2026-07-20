package ua.kucher.player.core.ui.presenter

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class Presenter {

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    init {
        Logger.d(
            messageString = "${this::class.simpleName}",
            tag = Presenter::class.simpleName.toString()
        )
    }

    open fun onCleared() {}

    internal fun clear() {
        onCleared()
        scope.cancel()
    }

}