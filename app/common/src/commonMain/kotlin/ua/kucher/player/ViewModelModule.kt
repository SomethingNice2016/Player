package ua.kucher.player

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ua.kucher.player.album.list.AlbumListViewModel
import ua.kucher.player.album.search.AlbumSearchViewModel
import ua.kucher.player.artist.list.ArtistListViewModel
import ua.kucher.player.artist.search.ArtistSearchViewModel
import ua.kucher.player.home.HomeViewModel
import ua.kucher.player.setting.SettingViewModel
import ua.kucher.player.song.list.SongListViewModel
import ua.kucher.player.song.search.SongsSearchViewModel
import ua.kucher.player.songplayer.MusicPlayerViewModel

internal val viewModelModule = module {

    viewModel {
        SongListViewModel(
            songRepository = get(),
            artistRepository = get(),
            albumRepository = get(),
            playbackController = get(),
            songMapper = get()
        )
    }

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