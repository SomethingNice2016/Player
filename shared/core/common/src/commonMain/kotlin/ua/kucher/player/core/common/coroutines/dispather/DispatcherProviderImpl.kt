package ua.kucher.player.core.common.coroutines.dispather

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal class DispatcherProviderImpl : DispatcherProvider {

    override val io: CoroutineDispatcher
        get() = Dispatchers.IO

    override val main: CoroutineDispatcher
        get() = Dispatchers.Main

    override val default: CoroutineDispatcher
        get() = Dispatchers.Default

}