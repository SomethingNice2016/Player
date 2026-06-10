package ua.kucher.player.songlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ua.kucher.player.core.common.datetime.TimeFormatter
import ua.kucher.player.data.song.SongRepository

internal class SongListViewModel(
    private val timeFormatter: TimeFormatter,
    private val songRepository: SongRepository
) : ViewModel() {

    val uiState: StateFlow<SongListUiState> = songRepository.getSongs().map { songs ->
        SongListUiState.success(
            songs = songs.map { song ->
                SongUi(
                    id = song.id,
                    title = song.title,
                    artwork = song.artwork,
                    artistName = song.artist?.name ?: "",
                    duration = timeFormatter.toFormatDuration(song.duration)
                )
            }
        )
    }.catch {
        emit(SongListUiState.Error)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SongListUiState.Loading
    )

}