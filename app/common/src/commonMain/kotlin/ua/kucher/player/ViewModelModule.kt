package ua.kucher.player

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ua.kucher.player.home.HomeViewModel
import ua.kucher.player.setting.SettingViewModel
import ua.kucher.player.songlist.SongListViewModel

internal val viewModelModule = module {
    viewModel {
        SongListViewModel(
            timeFormatter = get(),
            songRepository = get()
        )
    }
    viewModel { HomeViewModel() }
    viewModel { SettingViewModel() }
}