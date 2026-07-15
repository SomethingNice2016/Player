package ua.kucher.player.core.common.presenter

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope

abstract class Presenter(
    protected val scope: CoroutineScope
) {

    init {
        Logger.d(
            messageString = "${this::class.simpleName}",
            tag = Presenter::class.simpleName.toString()
        )
        println()
    }

}