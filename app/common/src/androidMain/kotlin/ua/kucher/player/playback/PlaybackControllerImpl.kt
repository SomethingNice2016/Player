package ua.kucher.player.playback

import androidx.media3.common.Player
import androidx.media3.session.MediaController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ua.kucher.player.core.common.coroutines.dispather.DispatcherProvider
import ua.kucher.player.entity.Playlist
import ua.kucher.player.entity.PlaylistItem
import kotlin.time.Duration.Companion.milliseconds

internal class PlaybackControllerImpl(
    private val dispatcherProvider: DispatcherProvider
) : PlaybackController {

    companion object {
        private const val PROGRESS_UPDATE_DELAY = 200L
    }

    private var controller: MediaController? = null

    private val scope = CoroutineScope(
        context = SupervisorJob() + dispatcherProvider.main.immediate
    )

    private var progressJob: Job? = null

    private val _currentItemId = MutableStateFlow<Long?>(null)
    private val _nextItemId = MutableStateFlow<Long?>(null)
    private val _previousItemId = MutableStateFlow<Long?>(null)
    private val _isPlaying = MutableStateFlow(false)
    private val _isShuffle = MutableStateFlow(false)
    private val _repeatMode = MutableStateFlow(PlaybackController.RepeatMode.OFF)
    private val _progress = MutableStateFlow(0L)
    private val _currentPlaylistId = MutableStateFlow<Long?>(null)

    override val currentItemId: Flow<Long?> =
        _currentItemId.asStateFlow()

    override val nextItemId: Flow<Long?> =
        _nextItemId.asStateFlow()

    override val previousItemId: Flow<Long?> =
        _previousItemId.asStateFlow()

    override val progress: Flow<Long> =
        _progress.asStateFlow()

    override val isPlaying: Flow<Boolean> =
        _isPlaying.asStateFlow()

    override val isShuffle: Flow<Boolean> =
        _isShuffle.asStateFlow()

    override val repeatMode: Flow<PlaybackController.RepeatMode> =
        _repeatMode.asStateFlow()

    private val playerListener = object : Player.Listener {
        override fun onEvents(
            player: Player,
            events: Player.Events
        ) {
            syncState()
            if (player.isPlaying) {
                startProgressUpdates()
            } else {
                stopProgressUpdates()
            }
        }
    }

    fun setController(controller: MediaController?) {
        stopProgressUpdates()
        this.controller?.removeListener(playerListener)
        this.controller = controller
        controller?.addListener(playerListener)
        syncState()
        if (controller?.isPlaying == true) {
            startProgressUpdates()
        }
    }

    override fun prepare(playlist: Playlist) = withController {
        if (_currentPlaylistId.value == playlist.id) return@withController
        clearMediaItems()
        setMediaItems(playlist.toMediaItems())
        prepare()
        _currentPlaylistId.value = playlist.id
        syncState()
    }

    override fun play(item: PlaylistItem) = withController {
        if (currentMediaItem?.mediaId == item.id.toString()) {
            playPause()
            return@withController
        }
        val existingIndex = mediaItems.indexOfFirst {
            it.mediaId.toLongOrNull() == item.id
        }
        if (existingIndex >= 0) {
            seekToDefaultPosition(existingIndex)
        } else {
            _currentPlaylistId.value = null
            setMediaItem(item.toMediaItem())
        }
        play()
        syncState()
    }

    override fun seekToPosition(position: Long) = withController {
        seekTo(position)
        _progress.value = currentPosition
    }

    override fun setShuffleMode(isShuffle: Boolean) {
        controller?.shuffleModeEnabled = isShuffle
    }

    override fun setRepeatMode(
        mode: PlaybackController.RepeatMode
    ) = withController {
        repeatMode = mode.toPlayerRepeatMode()
    }

    override fun playPause() = withController {
        if (isPlaying) {
            pause()
        } else {
            play()
        }
    }

    override fun forward() {
        controller?.seekToNextMediaItem()
    }

    override fun back() {
        controller?.seekToPrevious()
    }

    override fun release() {
        stopProgressUpdates()
        controller?.removeListener(playerListener)
        controller = null
    }

    private fun syncState() {
        val nonNullController = controller ?: run {
            _currentItemId.value = null
            _previousItemId.value = null
            _nextItemId.value = null
            _currentPlaylistId.value = null
            _isPlaying.value = false
            _progress.value = 0L
            return
        }
        _currentItemId.value = nonNullController.currentMediaItem?.mediaId?.toLongOrNull()
        _previousItemId.value = nonNullController.previousMediaItemId
        _nextItemId.value = nonNullController.nextMediaItemId
        _currentPlaylistId.value = nonNullController.currentMediaItem?.playlistId
        _repeatMode.value = PlaybackController.RepeatMode.fromPlayerRepeatMode(nonNullController.repeatMode)
        _isShuffle.value = nonNullController.shuffleModeEnabled
        _isPlaying.value = nonNullController.isPlaying
        _progress.value = nonNullController.currentPosition
    }

    private fun startProgressUpdates() {
        progressJob?.cancel()
        progressJob = scope.launch {
            while (currentCoroutineContext().isActive) {
                val controller = controller ?: break
                if (!controller.isPlaying) {
                    break
                }
                _progress.value = controller.currentPosition
                delay(PROGRESS_UPDATE_DELAY.milliseconds)
            }
        }
    }

    private fun stopProgressUpdates() {
        progressJob?.cancel()
        progressJob = null
    }

    private inline fun withController(
        action: MediaController.() -> Unit
    ) {
        controller?.action()
    }
}