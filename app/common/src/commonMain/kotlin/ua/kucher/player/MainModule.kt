package ua.kucher.player

import org.koin.dsl.module
import ua.kucher.player.core.common.coroutines.dispather.DispatcherProvider
import ua.kucher.player.core.common.datetime.TimeFormatter
import ua.kucher.player.core.common.datetime.TimeProvider
import ua.kucher.player.data.dataModule

val mainModule = module {

    includes(
        mainPlatformModule,
        dataModule,
        viewModelModule
    )

    single { DispatcherProvider.create() }
    single { TimeFormatter.create() }
    single { TimeProvider.create() }

}