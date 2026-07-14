package ua.kucher.player

import org.koin.dsl.module
import ua.kucher.player.common.SongUi
import ua.kucher.player.common.SongUiMapper
import ua.kucher.player.core.ui.coroutines.dispather.DispatcherProvider
import ua.kucher.player.core.ui.datetime.TimeFormatter
import ua.kucher.player.core.ui.datetime.TimeProvider
import ua.kucher.player.data.dataModule
import ua.kucher.player.entity.Song

val mainModule = module {

    includes(
        mainPlatformModule,
        viewModelModule,
        dataModule,
    )

    single { DispatcherProvider.create() }
    single { TimeFormatter.create() }
    single { TimeProvider.create() }

    factory<Song.Mapper<SongUi>> { SongUiMapper(get()) }

}