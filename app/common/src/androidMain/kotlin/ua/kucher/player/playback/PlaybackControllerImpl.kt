package ua.kucher.player.playback

import android.os.Handler
import android.os.Looper
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.session.MediaController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ua.kucher.player.entity.Playlist
import ua.kucher.player.entity.PlaylistItem

internal class PlaybackControllerImpl : PlaybackController {

    companion object {
        private const val DELAY = 200L
    }

    private var controller: MediaController? = null

    private val handler = Handler(Looper.getMainLooper())

    private val _currentItemId = MutableStateFlow<Long?>(null)

    private val _nextItemId = MutableStateFlow<Long?>(null)

    private val _previousItemId = MutableStateFlow<Long?>(null)

    private val _isPlaying = MutableStateFlow(false)

    private val _isShuffle = MutableStateFlow(false)

    private val _repeatMode = MutableStateFlow(PlaybackController.RepeatMode.OFF)

    private val _progress = MutableStateFlow(0L)

    private val _currentPlaylistId = MutableStateFlow<Long?>(null)

    override val currentItemId: Flow<Long?>
        get() = _currentItemId

    override val nextItemId: Flow<Long?>
        get() = _nextItemId

    override val previousItemId: Flow<Long?>
        get() = _previousItemId

    override val progress: Flow<Long>
        get() = _progress

    override val isPlaying: Flow<Boolean>
        get() = _isPlaying

    override val isShuffle: Flow<Boolean>
        get() = _isShuffle

    override val repeatMode: Flow<PlaybackController.RepeatMode>
        get() = _repeatMode

    private val playerListener = object : Player.Listener {

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            _currentItemId.value = mediaItem?.mediaId?.toLongOrNull()
            _previousItemId.value = controller?.previousMediaItemId
            _nextItemId.value = controller?.nextMediaItemId
        }

        override fun onTimelineChanged(
            timeline: Timeline,
            reason: Int
        ) {
            if (timeline.isEmpty) {
                _currentPlaylistId.value = null
                return
            }
            _currentPlaylistId.value = controller?.currentMediaItem?.playlistId
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            _isShuffle.value = shuffleModeEnabled
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
            _repeatMode.value = PlaybackController.RepeatMode.fromPlayerRepeatMode(repeatMode)
        }
    }

    private val playbackStateRunnable = object : Runnable {
        override fun run() {
            withController {
                _progress.value = currentPosition
                _isPlaying.value = isPlaying
            }
            handler.postDelayed(this, DELAY)
        }
    }

    fun setController(controller: MediaController?) {
        this.controller?.removeListener(playerListener)
        this.controller = controller
        this.controller?.addListener(playerListener)
        setupState()
        handler.removeCallbacks(playbackStateRunnable)
        if (controller != null) {
            handler.postDelayed(playbackStateRunnable, DELAY)
        }
    }

    override fun prepare(playlist: Playlist) = withController {
        if (_currentPlaylistId.value == playlist.id) return@withController
        clearMediaItems()
        setMediaItems(playlist.toMediaItems())
        prepare()
    }

    override fun play(item: PlaylistItem) = withController {
        if (currentMediaItem?.mediaId == item.id.toString()) {
            playPause()
            return@withController
        }

        val index = controller?.mediaItems?.indexOfFirst {
            it.mediaId.toLongOrNull() == item.id
        } ?: run {
            _currentPlaylistId.value = null
            setMediaItem(item.toMediaItem())
            0
        }

        if (index == -1) return@withController

        seekToDefaultPosition(index)
        _currentItemId.value = item.id
        play()
    }

    override fun seekToPosition(position: Long) = withController {
        withoutStateHandler {
            seekTo(position)
            _progress.value = position
        }
    }

    override fun setShuffleMode(isShuffle: Boolean) {
        controller?.shuffleModeEnabled = isShuffle
    }

    override fun setRepeatMode(mode: PlaybackController.RepeatMode) = withController {
        repeatMode = when (mode) {
            PlaybackController.RepeatMode.OFF -> Player.REPEAT_MODE_OFF
            PlaybackController.RepeatMode.ALL -> Player.REPEAT_MODE_ALL
            PlaybackController.RepeatMode.ONE -> Player.REPEAT_MODE_ONE
        }
    }

    override fun playPause() {
        controller?.playPause()
    }

    override fun forward() = withoutStateHandler {
        controller?.seekToNextMediaItem()
    }

    override fun back() = withoutStateHandler {
        controller?.seekToPrevious()
    }

    private fun setupState() {
        _currentItemId.value = controller?.currentMediaItem?.mediaId?.toLongOrNull()
        _previousItemId.value = controller?.previousMediaItemId
        _nextItemId.value = controller?.nextMediaItemId
        _currentPlaylistId.value = controller?.currentMediaItem?.playlistId
        withController {
            _repeatMode.value = PlaybackController.RepeatMode.fromPlayerRepeatMode(repeatMode)
            _isShuffle.value = shuffleModeEnabled
            _isPlaying.value = isPlaying
        }
    }

    private fun withController(action: MediaController.() -> Unit) {
        _previousItemId.value = controller?.previousMediaItemId
        _nextItemId.value = controller?.nextMediaItemId
        controller?.let { nonNullController ->
            action.invoke(nonNullController)
        }
    }

    private fun withoutStateHandler(block: () -> Unit) {
        handler.removeCallbacks(playbackStateRunnable)
        block()
        handler.postDelayed(playbackStateRunnable, DELAY)
    }
}
