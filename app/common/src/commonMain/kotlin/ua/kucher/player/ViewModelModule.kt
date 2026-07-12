package ua.kucher.player

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ua.kucher.player.artistlist.ArtistListViewModel
import ua.kucher.player.artistsearch.ArtistSearchViewModel
import ua.kucher.player.home.HomeViewModel
import ua.kucher.player.setting.SettingViewModel
import ua.kucher.player.songlist.SongListViewModel
import ua.kucher.player.songplayer.MusicPlayerViewModel
import ua.kucher.player.songsearch.SongsSearchViewModel

internal val viewModelModule = module {

    viewModel {
        SongListViewModel(
            timeFormatter = get(),
            songRepository = get(),
            artistRepository = get(),
            albumRepository = get(),
            playbackController = get()
        )
    }

    viewModel {
        MusicPlayerViewModel(
            playbackController = get(),
            timeFormatter = get(),
            songRepository = get(),
        )
    }

    viewModel {
        SongsSearchViewModel(
            timeFormatter = get(),
            playbackController = get(),
            songRepository = get()
        )
    }

    viewModel {
        HomeViewModel(
            songRepository = get(),
            artistRepository = get(),
            albumRepository = get(),
            playbackController = get()
        )
    }

    viewModel {
        ArtistListViewModel(
            playbackController = get(),
            artistRepository = get()
        )
    }

    viewModel {
        ArtistSearchViewModel(
            playbackController = get(),
            artistRepository = get()
        )
    }

    viewModel { SettingViewModel() }
}