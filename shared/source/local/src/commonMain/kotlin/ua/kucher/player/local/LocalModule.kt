package ua.kucher.player.local

import androidx.room.RoomDatabase
import org.koin.dsl.module
import ua.kucher.player.local.album.AlbumLocalSource
import ua.kucher.player.local.album.AlbumLocalSourceImpl
import ua.kucher.player.local.artist.ArtistLocalSource
import ua.kucher.player.local.artist.ArtistLocalSourceImpl
import ua.kucher.player.local.song.SongLocalSource
import ua.kucher.player.local.song.SongLocalSourceImpl

val localModule = module {

    includes(localPlatformModule)

    single {
        get<RoomDatabase.Builder<PlayerDatabase>>()
            .fallbackToDestructiveMigration(true)
            .setQueryCoroutineContext(get<ua.kucher.player.core.common.coroutines.dispather.DispatcherProvider>().io)
            .build()
    }

    factory { get<PlayerDatabase>().getSongDao() }
    factory { get<PlayerDatabase>().getAlbumDao() }
    factory { get<PlayerDatabase>().getArtistDao() }
    factory { get<PlayerDatabase>().getPlaylistDao() }

    single<AlbumLocalSource> {
        AlbumLocalSourceImpl(
            localStorageSource = get(),
            albumDao = get()
        )
    }

    single<ArtistLocalSource> {
        ArtistLocalSourceImpl(
            localStorageSource = get(),
            artistDao = get()
        )
    }

    single<SongLocalSource> {
        SongLocalSourceImpl(
            localStorageSource = get(),
            dispatcherProvider = get(),
            artworkCache = get(),
            songDao = get(),
        )
    }
}