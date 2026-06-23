package ua.kucher.player.core.common.coroutines.dispather

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainCoroutineDispatcher

internal class DispatcherProviderImpl : DispatcherProvider {

    override val io: CoroutineDispatcher = Dispatchers.IO

    override val main: MainCoroutineDispatcher = Dispatchers.Main

    override val default: CoroutineDispatcher = Dispatchers.Default

    override val artworkCache: CoroutineDispatcher = io.limitedParallelism(4)

}