package ua.kucher.player

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ua.kucher.player.home.HomeViewModel
import ua.kucher.player.search.SearchViewModel
import ua.kucher.player.setting.SettingViewModel
import ua.kucher.player.songlist.SongListViewModel
import ua.kucher.player.songplayer.MusicPlayerViewModel

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
            songRepository = get()
        )
    }

    viewModel {
        SearchViewModel(
            timeFormatter = get(),
            playbackController = get(),
            songRepository = get()
        )
    }

    viewModel {
        HomeViewModel(
            songRepository = get(),
            artistRepository = get(),
            albumRepository = get()
        )
    }

    viewModel { SettingViewModel() }
}