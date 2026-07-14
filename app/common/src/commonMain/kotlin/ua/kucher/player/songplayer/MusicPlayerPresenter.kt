package ua.kucher.player.songplayer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ua.kucher.player.common.SongUi
import ua.kucher.player.core.ui.coroutines.combineNotNull
import ua.kucher.player.core.ui.coroutines.flatMapNotNullLatest
import ua.kucher.player.core.ui.coroutines.mapNotNull
import ua.kucher.player.core.ui.datetime.TimeFormatter
import ua.kucher.player.core.ui.presenter.Presenter
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.entity.Song
import ua.kucher.player.playback.PlaybackController

@OptIn(ExperimentalCoroutinesApi::class)
internal class MusicPlayerPresenter(
    private val playbackController: PlaybackController,
    private val timeFormatter: TimeFormatter,
    private val songRepository: SongRepository,
    private val songMapper: Song.Mapper<SongUi>,
    scope: CoroutineScope
) : Presenter(scope) {

    private val currentSong = playbackController.state.map { playbackState ->
        playbackState.currentItemId
    }.flatMapNotNullLatest { id ->
        songRepository.getSongById(id).mapNotNull { song ->
            songMapper.map(song)
        }
    }

    val uiState = combineNotNull(
        currentSong,
        playbackController.state
    ) { song, playbackState ->
        MusicPlayerUiState(
            currentSong = song,
            displayProgress = timeFormatter.formatDuration(playbackState.progress),
            progress = playbackState.progress,
            isPlaying = playbackState.isPlaying,
            isShuffle = playbackState.isShuffle,
            repeatMode = playbackState.repeatMode,
            artworks = playbackState.artworks
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

    fun playById(id: Long) {
        scope.launch {
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
        scope.launch {
            uiState.firstOrNull()?.isShuffle?.let { shuffle ->
                playbackController.setShuffleMode(!shuffle)
            }
        }
    }

    fun repeat() {
        scope.launch {
            uiState.firstOrNull()?.repeatMode?.let { repeat ->
                playbackController.setRepeatMode(repeat.getNext())
            }
        }
    }
}