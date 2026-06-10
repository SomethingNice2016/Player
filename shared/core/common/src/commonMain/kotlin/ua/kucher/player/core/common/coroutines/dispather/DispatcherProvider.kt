package ua.kucher.player.core.common.coroutines.dispather

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    companion object {
        fun get(): DispatcherProvider = DispatcherProviderImpl()
    }

    val io: CoroutineDispatcher

    val main: CoroutineDispatcher

    val default: CoroutineDispatcher

}