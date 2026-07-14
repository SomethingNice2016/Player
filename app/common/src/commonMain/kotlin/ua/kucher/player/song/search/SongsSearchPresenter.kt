package ua.kucher.player.song.search

import kotlinx.coroutines.CoroutineScope
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
import ua.kucher.player.core.ui.presenter.Presenter
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.entity.Song
import ua.kucher.player.playback.PlaybackController

@OptIn(ExperimentalCoroutinesApi::class)
internal class SongsSearchPresenter(
    private val songRepository: SongRepository,
    private val playbackController: PlaybackController,
    private val songMapper: Song.Mapper<SongUi>,
    scope: CoroutineScope
) : Presenter(scope) {

    private val searchQuery = MutableStateFlow("")

    private val searchResult = searchQuery.flatMapLatest { query ->
        songRepository.searchSongsByTitle(query)
    }.map { songs ->
        songs.map { song ->
            songMapper.map(song)
        }
    }

    val uiState = combine(
        searchQuery,
        searchResult,
        playbackController.state,
    ) { query, result, playbackState ->
        SongsSearchUiState(
            searchQuery = query,
            searchResult = result,
            playingSongId = playbackState.currentItemId,
            isPlaying = playbackState.isPlaying,
        )
    }.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = SongsSearchUiState()
    )

    fun search(query: String) {
        searchQuery.value = query
    }

    fun playSong(id: Long) {
        scope.launch {
            if (playbackController.inQueue(id)) {
                songRepository.getSongById(id).firstOrNull()?.let { song ->
                    playbackController.play(song)
                }
            } else {
                val songs = songRepository.getAllSongs().firstOrNull() ?: return@launch
                val song = songs.findLast { song -> song.id == id } ?: return@launch
                playbackController.play(
                    playlist = songs,
                    item = song
                )
            }
        }
    }
}