package ua.kucher.player.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.kucher.player.common.SongUi
import ua.kucher.player.core.common.datetime.TimeFormatter
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.playback.PlaybackController

@OptIn(ExperimentalCoroutinesApi::class)
internal class SearchViewModel(
    private val songRepository: SongRepository,
    private val timeFormatter: TimeFormatter,
    private val playbackController: PlaybackController
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")

    private val searchResult = searchQuery.flatMapLatest { query ->
        songRepository.searchSongsByTitle(query)
    }.map { songs ->
        songs.map { song ->
            SongUi(
                id = song.id,
                title = song.title,
                artwork = song.artwork,
                artistName = song.artist?.name ?: "",
                duration = song.duration,
                displayDuration = timeFormatter.toFormatDuration(song.duration)
            )
        }
    }

    val uiState = combine(
        searchQuery,
        searchResult,
        playbackController.state,
    ) { query, result, playbackState ->
        SearchUiState(
            searchQuery = query,
            searchResult = result,
            playingSongId = playbackState.currentItemId,
            isPlaying = playbackState.isPlaying,
            isPlayerShowed = playbackState.currentItemId != null
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SearchUiState()
    )

    fun search(query: String) {
        searchQuery.value = query
    }

    fun playSong(id: Long) {
        viewModelScope.launch {
            val song = if (playbackController.inQueue(id)) {
                songRepository.getSongById(id).firstOrNull() ?: return@launch
            } else {
                val songs = songRepository.getAllSongs().firstOrNull() ?: return@launch
                playbackController.prepare(songs)
                songs.findLast { song -> song.id == id } ?: return@launch
            }
            playbackController.play(song)
        }
    }

}