package ua.kucher.player.data

import org.koin.dsl.module
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.data.song.SongRepositoryImpl

val dataModule = module {
    single<SongRepository> {
        SongRepositoryImpl(
            songLocalSource = get(),
            albumLocalSource = get(),
            artistLocalSource = get()
        )
    }
}