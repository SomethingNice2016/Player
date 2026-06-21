package ua.kucher.player.local

import org.koin.dsl.module
import ua.kucher.player.database.KucherPlayerDatabase
import ua.kucher.player.local.album.AlbumLocalSource
import ua.kucher.player.local.album.AlbumLocalSourceImpl
import ua.kucher.player.local.artist.ArtistLocalSource
import ua.kucher.player.local.artist.ArtistLocalSourceImpl
import ua.kucher.player.local.song.SongLocalSource
import ua.kucher.player.local.song.SongLocalSourceImpl

val localModule = module {

    includes(localPlatformModule)

    single { KucherPlayerDatabase(get<DatabaseDriverFactory>().createDriver()) }

    factory { get<KucherPlayerDatabase>().songEntityQueries }
    factory { get<KucherPlayerDatabase>().albumEntityQueries }
    factory { get<KucherPlayerDatabase>().artisEntityQueries }

    single<AlbumLocalSource> {
        AlbumLocalSourceImpl(
            localStorageSource = get(),
            albumEntityQueries = get()
        )
    }

    single<ArtistLocalSource> {
        ArtistLocalSourceImpl(
            localStorageSource = get(),
            artistEntityQueries = get()
        )
    }

    single<SongLocalSource> {
        SongLocalSourceImpl(
            localStorageSource = get(),
            dispatcherProvider = get(),
            artworkCache = get(),
            songEntityQueries = get(),
        )
    }
}