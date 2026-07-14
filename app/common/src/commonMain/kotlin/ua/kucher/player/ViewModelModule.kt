package ua.kucher.player

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ua.kucher.player.album.list.AlbumListViewModel
import ua.kucher.player.album.search.AlbumSearchViewModel
import ua.kucher.player.artist.list.ArtistListViewModel
import ua.kucher.player.artist.search.ArtistSearchViewModel
import ua.kucher.player.home.HomeViewModel
import ua.kucher.player.setting.SettingViewModel
import ua.kucher.player.song.allsongs.AllSongViewModel
import ua.kucher.player.song.favorite.FavoriteSongViewModel
import ua.kucher.player.song.menu.SongMenuViewModel
import ua.kucher.player.song.search.SongsSearchViewModel
import ua.kucher.player.songplayer.MusicPlayerViewModel

internal val viewModelModule = module {

    viewModel {
        MusicPlayerViewModel(
            playbackController = get(),
            songRepository = get(),
            songMapper = get(),
            timeFormatter = get()
        )
    }

    viewModel {
        SongsSearchViewModel(
            playbackController = get(),
            songRepository = get(),
            songMapper = get()
        )
    }

    viewModel {
        HomeViewModel(
            songRepository = get(),
            artistRepository = get(),
            albumRepository = get(),
            playbackController = get(),
            songMapper = get()
        )
    }

    viewModel {
        AllSongViewModel(
            songRepository = get(),
            artistRepository = get(),
            albumRepository = get(),
            playbackController = get(),
            songMapper = get()
        )
    }

    viewModel {
        FavoriteSongViewModel(
            songRepository = get(),
            artistRepository = get(),
            albumRepository = get(),
            playbackController = get(),
            songMapper = get()
        )
    }

    viewModel {
        SongMenuViewModel(
            playbackController = get(),
            songRepository = get()
        )
    }

    viewModel {
        ArtistListViewModel(
            artistRepository = get()
        )
    }

    viewModel {
        ArtistSearchViewModel(
            artistRepository = get()
        )
    }

    viewModel {
        AlbumListViewModel(
            albumRepository = get()
        )
    }

    viewModel {
        AlbumSearchViewModel(
            albumRepository = get()
        )
    }

    viewModel { SettingViewModel() }
}