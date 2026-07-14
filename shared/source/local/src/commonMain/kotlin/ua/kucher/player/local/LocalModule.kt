package ua.kucher.player.local

import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import org.koin.dsl.module
import ua.kucher.player.core.ui.coroutines.dispather.DispatcherProvider
import ua.kucher.player.local.album.AlbumLocalSource
import ua.kucher.player.local.album.AlbumLocalSourceImpl
import ua.kucher.player.local.artist.ArtistLocalSource
import ua.kucher.player.local.artist.ArtistLocalSourceImpl
import ua.kucher.player.local.playlist.PlaylistEntity
import ua.kucher.player.local.song.SongLocalSource
import ua.kucher.player.local.song.SongLocalSourceImpl

val localModule = module {

    includes(localPlatformModule)

    single {
        get<RoomDatabase.Builder<PlayerDatabase>>()
            .fallbackToDestructiveMigration(true)
            .setQueryCoroutineContext(get<DispatcherProvider>().io)
            .addCallback(
                callback = object : RoomDatabase.Callback() {
                    override fun onCreate(connection: SQLiteConnection) {
                        super.onCreate(connection)
                        connection.execSQL(PlaylistEntity.createFavoriteQuery())
                    }
                }
            ).build()
    }

    factory { get<PlayerDatabase>().getSongDao() }
    factory { get<PlayerDatabase>().getAlbumDao() }
    factory { get<PlayerDatabase>().getArtistDao() }

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