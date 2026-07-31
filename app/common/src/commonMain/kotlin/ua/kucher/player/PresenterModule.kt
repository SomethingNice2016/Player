package ua.kucher.player

import org.koin.dsl.module
import ua.kucher.player.album.detail.AlbumDetailPresenter
import ua.kucher.player.album.list.AlbumListPresenter
import ua.kucher.player.album.search.AlbumSearchPresenter
import ua.kucher.player.artist.detail.ArtistDetailPresenter
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

    factory {
        MusicPlayerPresenter(
            playbackController = get(),
            songRepository = get(),
            songMapper = get(),
            timeFormatter = get(),
            clipboardController = get(),
        )
    }

    factory {
        SongsSearchPresenter(
            playbackController = get(),
            songRepository = get(),
            songMapper = get(),
        )
    }

    factory {
        HomePresenter(
            songRepository = get(),
            artistRepository = get(),
            albumRepository = get(),
            playbackController = get(),
            songMapper = get(),
        )
    }

    factory {
        AllSongPresenter(
            songRepository = get(),
            artistRepository = get(),
            albumRepository = get(),
            playbackController = get(),
            songMapper = get(),
        )
    }

    factory {
        FavoriteSongPresenter(
            songRepository = get(),
            artistRepository = get(),
            albumRepository = get(),
            playbackController = get(),
            songMapper = get(),
        )
    }

    factory {
        ArtistListPresenter(
            artistRepository = get(),
        )
    }

    factory {
        ArtistSearchPresenter(
            artistRepository = get(),
        )
    }

    factory {
        AlbumListPresenter(
            albumRepository = get(),
        )
    }

    factory {
        AlbumSearchPresenter(
            albumRepository = get(),
        )
    }

    factory {
        SettingPresenter()
    }

    factory { (songId: Long) ->
        SongMenuPresenter(
            songId = songId,
            mapper = get(),
            playbackController = get(),
            sharingManager = get(),
            songRepository = get(),
        )
    }

    factory { (albumId: Long) ->
        AlbumDetailPresenter(
            albumId = albumId,
            albumRepository = get(),
            songRepository = get(),
            playbackController = get(),
            mapper = get()
        )
    }

    factory { (artistId: Long) ->
        ArtistDetailPresenter(
            artistId = artistId,
            artistRepository = get(),
            albumRepository = get(),
            songRepository = get(),
            playbackController = get(),
            mapper = get()
        )
    }
}