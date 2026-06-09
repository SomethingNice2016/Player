package ua.kucher.player.songlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import ua.kucher.player.data.song.SongRepository

internal class SongListViewModel(private val songRepository: SongRepository): ViewModel() {

    val songList = songRepository.getSongs()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

}