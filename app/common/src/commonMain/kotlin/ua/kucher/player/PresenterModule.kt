package ua.kucher.player

import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module
import ua.kucher.player.album.list.AlbumListPresenter
import ua.kucher.player.album.search.AlbumSearchPresenter
import ua.kucher.player.artist.list.ArtistListPresenter
import ua.kucher.player.artist.search.ArtistSearchPresenter
import ua.kucher.player.home.HomePresenter
import ua.kucher.player.setting.SettingPresenter
import ua.kucher.player.song.allsongs.AllSongPresenter
import ua.kucher.player.song.favorite.FavoriteSongPresenter
import ua.kucher.player.song.menu.SongMenuPresenter
import ua.kucher.player.song.search.SongsSearchPresenter
import ua.kucher.player.songplayer.MusicPlayerPresenter

internal val presenterModule = module {

    factory { (scope: CoroutineScope) ->
        MusicPlayerPresenter(
            playbackController = get(),
            songRepository = get(),
            songMapper = get(),
            timeFormatter = get(),
            scope = scope
        )
    }

    factory { (scope: CoroutineScope) ->
        SongsSearchPresenter(
            playbackController = get(),
            songRepository = get(),
            songMapper = get(),
            scope = scope
        )
    }

    factory { (scope: CoroutineScope) ->
        HomePresenter(
            songRepository = get(),
            artistRepository = get(),
            albumRepository = get(),
            playbackController = get(),
            songMapper = get(),
            scope = scope
        )
    }

    factory { (scope: CoroutineScope) ->
        AllSongPresenter(
            songRepository = get(),
            artistRepository = get(),
            albumRepository = get(),
            playbackController = get(),
            songMapper = get(),
            scope = scope
        )
    }

    factory { (scope: CoroutineScope) ->
        FavoriteSongPresenter(
            songRepository = get(),
            artistRepository = get(),
            albumRepository = get(),
            playbackController = get(),
            songMapper = get(),
            scope = scope
        )
    }

    factory { (scope: CoroutineScope) ->
        ArtistListPresenter(
            artistRepository = get(),
            scope = scope
        )
    }

    factory { (scope: CoroutineScope) ->
        ArtistSearchPresenter(
            artistRepository = get(),
            scope = scope
        )
    }

    factory { (scope: CoroutineScope) ->
        AlbumListPresenter(
            albumRepository = get(),
            scope = scope
        )
    }

    factory { (scope: CoroutineScope) ->
        AlbumSearchPresenter(
            albumRepository = get(),
            scope = scope
        )
    }

    factory { (scope: CoroutineScope) ->
        SettingPresenter(
            scope = scope
        )
    }

    factory { (scope: CoroutineScope, id: Long) ->
        SongMenuPresenter(
            songId = id,
            mapper = get(),
            playbackController = get(),
            songRepository = get(),
            scope = scope,
        )
    }
}