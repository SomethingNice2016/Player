package ua.kucher.player.data

import org.koin.dsl.module
import ua.kucher.player.data.albun.AlbumRepository
import ua.kucher.player.data.albun.AlbumRepositoryImpl
import ua.kucher.player.data.artist.ArtistRepository
import ua.kucher.player.data.artist.ArtistRepositoryImpl
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.data.song.SongRepositoryImpl
import ua.kucher.player.local.localModule

val dataModule = module {

    includes(localModule)

    single<ArtistRepository> {
        ArtistRepositoryImpl(
            dispatcherProvider = get(),
            artistLocalSource = get()
        )
    }

    single<AlbumRepository> {
        AlbumRepositoryImpl(
            dispatcherProvider = get(),
            albumLocalSource = get()
        )
    }

    single<SongRepository> {
        SongRepositoryImpl(
            dispatcherProvider = get(),
            songLocalSource = get(),
        )
    }
}