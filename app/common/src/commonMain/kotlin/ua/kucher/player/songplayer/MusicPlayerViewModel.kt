package ua.kucher.player.songplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ua.kucher.player.common.SongUi
import ua.kucher.player.core.common.coroutines.combineNotNull
import ua.kucher.player.core.common.coroutines.flatMapNotNullLatest
import ua.kucher.player.core.common.coroutines.mapNotNull
import ua.kucher.player.core.common.datetime.TimeFormatter
import ua.kucher.player.data.artist.ArtistRepository
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.playback.PlaybackController

@OptIn(ExperimentalCoroutinesApi::class)
internal class MusicPlayerViewModel(
    private val playbackController: PlaybackController,
    private val timeFormatter: TimeFormatter,
    private val songRepository: SongRepository,
    private val artistRepository: ArtistRepository
) : ViewModel() {

    private val mutex = Mutex()

    private val currentSong = playbackController.state.map { playbackState ->
        playbackState.currentItemId
    }.flatMapNotNullLatest { id ->
        songRepository.getSongById(id).mapNotNull { song ->
            SongUi(
                id = song.id,
                title = song.title,
                artistName = song.artistTitle ?: "",
                displayDuration = timeFormatter.toFormatDuration(song.duration),
                duration = song.duration,
                artwork = song.artwork
            )
        }
    }

    val uiState = combineNotNull(
        currentSong,
        playbackController.state
    ) { song, playbackState ->
        MusicPlayerUiState(
            currentSong = song,
            displayProgress = timeFormatter.toFormatDuration(playbackState.progress),
            progress = playbackState.progress,
            isPlaying = playbackState.isPlaying,
            isShuffle = playbackState.isShuffle,
            repeatMode = playbackState.repeatMode,
            artworks = playbackState.artworks
        )
    }

    init {
        playbackController.setItemChangeListener { songId ->
            viewModelScope.launch {
                mutex.withLock {
                    songRepository.incListenCount(songId)
                    songRepository.getSongById(songId).firstOrNull()?.let { song ->
                        song.artist?.let { artist ->
                            artistRepository.incListenCount(artist.id)
                        }
                    }
                }
            }
        }
    }

    fun forward() {
        playbackController.forward()
    }

    fun back() {
        playbackController.back()
    }

    fun playPause() {
        playbackController.playPause()
    }

    fun playById(id: Long) {
        viewModelScope.launch {
            uiState.firstOrNull()?.let { state ->
                if (id != state.currentSong.id)
                    songRepository.getSongById(id).firstOrNull()?.let { song ->
                        playbackController.play(song)
                    }
            }
        }
    }

    fun seekToPosition(position: Long) {
        playbackController.seekToPosition(position)
    }

    fun shuffle() {
        viewModelScope.launch {
            uiState.firstOrNull()?.isShuffle?.let { shuffle ->
                playbackController.setShuffleMode(!shuffle)
            }
        }
    }

    fun repeat() {
        viewModelScope.launch {
            uiState.firstOrNull()?.repeatMode?.let { repeat ->
                playbackController.setRepeatMode(repeat.getNext())
            }
        }
    }
}