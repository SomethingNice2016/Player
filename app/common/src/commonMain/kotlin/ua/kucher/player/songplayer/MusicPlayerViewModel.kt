package ua.kucher.player.songplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ua.kucher.player.SongUi
import ua.kucher.player.core.common.coroutines.combine
import ua.kucher.player.core.common.coroutines.combineNotNull
import ua.kucher.player.core.common.coroutines.flatMapNotNullLatest
import ua.kucher.player.core.common.datetime.TimeFormatter
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.playback.PlaybackController

@OptIn(ExperimentalCoroutinesApi::class)
internal class MusicPlayerViewModel(
    private val playbackController: PlaybackController,
    private val timeFormatter: TimeFormatter,
    private val songRepository: SongRepository
) : ViewModel() {

    //TODO Add a view pager later so you can swipe between tracks.
    private val currentSong = playbackController.currentItemId
        .flatMapNotNullLatest { id ->
            songRepository.getSongById(id).map { song ->
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

    private val previousSong = playbackController.previousItemId
        .flatMapNotNullLatest { id ->
            songRepository.getSongById(id).map { song ->
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

    private val nextSong = playbackController.nextItemId
        .flatMapNotNullLatest { id ->
            songRepository.getSongById(id).map { song ->
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
        playbackController.progress,
        playbackController.isPlaying,
        playbackController.isShuffle,
        playbackController.repeatMode
    ) { song, progress, isPlaying, isShuffle, repeatMode ->
        MusicPlayerUiState(
            currentSong = song,
            displayProgress = timeFormatter.toFormatDuration(progress),
            progress = progress,
            isPlaying = isPlaying,
            isShuffle = isShuffle,
            repeatMode = repeatMode,
            previousSong = null,
            nextSong = null
        )
    }.combine(
        nextSong,
        previousSong
    ) { state, next, previous ->
        state?.copy(
            nextSong = next,
            previousSong = previous
        )
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