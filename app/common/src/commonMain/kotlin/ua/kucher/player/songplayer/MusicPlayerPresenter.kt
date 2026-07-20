package ua.kucher.player.songplayer

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ua.kucher.player.common.SongUi
import ua.kucher.player.core.common.clipboard.ClipboardController
import ua.kucher.player.core.common.coroutines.combineNotNull
import ua.kucher.player.core.common.coroutines.flatMapNotNullLatest
import ua.kucher.player.core.common.coroutines.mapNotNull
import ua.kucher.player.core.common.datetime.TimeFormatter
import ua.kucher.player.core.ui.presenter.Presenter
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.entity.Song
import ua.kucher.player.playback.PlaybackController

@OptIn(ExperimentalCoroutinesApi::class)
internal class MusicPlayerPresenter(
    private val playbackController: PlaybackController,
    private val timeFormatter: TimeFormatter,
    private val clipboardController: ClipboardController,
    private val songRepository: SongRepository,
    private val songMapper: Song.Mapper<SongUi>,
) : Presenter() {

    private val eventChanner = Channel<MusicPlayerEvent>(capacity = Channel.BUFFERED)

    private val currentSong = playbackController.state.map { playbackState ->
        playbackState.currentItemId
    }.flatMapNotNullLatest { id ->
        songRepository.getSongById(id).mapNotNull { song ->
            songMapper.map(song)
        }
    }

    val event: Flow<MusicPlayerEvent>
        get() = eventChanner.receiveAsFlow()

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

    fun collapsePlayer() {
        scope.launch {
            eventChanner.send(MusicPlayerEvent.CollapsePlayer)
        }
    }

    fun expandPlayer() {
        scope.launch {
            eventChanner.send(MusicPlayerEvent.ExpandPlayer)
        }
    }

    fun copySongTitle() {
        scope.launch {
            uiState.firstOrNull()?.currentSong?.title?.let { title ->
                clipboardController.setText(title)
            }
        }
    }

    fun copyArtistName() {
        scope.launch {
            uiState.firstOrNull()?.currentSong?.artistName?.let { artistName ->
                clipboardController.setText(artistName)
            }
        }
    }
}