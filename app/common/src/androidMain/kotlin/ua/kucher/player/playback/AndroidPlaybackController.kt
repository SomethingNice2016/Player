package ua.kucher.player.playback

import androidx.media3.common.Player
import androidx.media3.session.MediaController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ua.kucher.player.entity.PlaylistItem
import kotlin.time.Duration.Companion.milliseconds

internal class AndroidPlaybackController : PlaybackController {

    companion object {
        private const val PROGRESS_UPDATE_DELAY = 300L
    }

    private var controller: MediaController? = null

    private val scope = CoroutineScope(
        context = SupervisorJob() + Dispatchers.Main.immediate
    )

    private var progressJob: Job? = null

    private val _state = MutableStateFlow(PlaybackState())

    override val state = _state.asStateFlow()

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

    override fun prepare(playlist: List<PlaylistItem>) = withController {
        clearMediaItems()
        setMediaItems(playlist.map { it.toMediaItem() })
        prepare()
    }

    override fun play(item: PlaylistItem) = withController {
        if (currentMediaItem?.mediaId == item.id.toString()) {
            playPause()
            return@withController
        }
        val existingIndex = mediaItems.indexOfFirst { mediaItem ->
            mediaItem.mediaId.toLongOrNull() == item.id
        }
        if (existingIndex >= 0) {
            seekToDefaultPosition(existingIndex)
        } else {
            setMediaItem(item.toMediaItem())
        }
        play()
        syncState()
    }

    override fun playNext(item: PlaylistItem) = withController {
        val currentIndex = currentMediaItemIndex
        val insertIndex = (currentIndex.inc()).coerceAtMost(mediaItems.size)
        val existingIndex = mediaItems.indexOfFirst { mediaItem ->
            mediaItem.mediaId.toLongOrNull() == item.id
        }
        if (existingIndex >= 0) {
            moveMediaItem(existingIndex, insertIndex)
        } else {
            addMediaItem(insertIndex, item.toMediaItem())
        }
        syncState()
    }

    override fun seekToPosition(position: Long) = withController {
        seekTo(position)
        _state.update { value -> value.copy(progress = currentPosition) }
    }

    override fun setShuffleMode(isShuffle: Boolean) {
        controller?.shuffleModeEnabled = isShuffle
    }

    override fun setRepeatMode(
        mode: PlaybackController.RepeatMode
    ) = withController {
        repeatMode = mode.toPlayerRepeatMode()
    }

    override fun inQueue(id: Long): Boolean {
        return !controller?.mediaItems?.filter { item ->
            item.mediaId.toLongOrNull() == id
        }.isNullOrEmpty()
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
            _state.update {
                PlaybackState(
                    currentItemId = null,
                    isPlaying = false,
                    isShuffle = false,
                    repeatMode = PlaybackController.RepeatMode.OFF,
                    progress = 0L,
                    artworks = mapOf(),
                )
            }
            return
        }
        _state.update {
            PlaybackState(
                currentItemId = nonNullController.currentMediaItem?.mediaId?.toLongOrNull(),
                isPlaying = nonNullController.isPlaying,
                isShuffle = nonNullController.shuffleModeEnabled,
                repeatMode = PlaybackController.RepeatMode.fromPlayerRepeatMode(nonNullController.repeatMode),
                progress = nonNullController.currentPosition,
                artworks = buildMap {
                    nonNullController.mediaItems.forEach { mediaItem ->
                        val id = mediaItem.mediaId.toLongOrNull() ?: return@forEach
                        put(id, mediaItem.mediaMetadata.artworkUri?.toString().orEmpty())
                    }
                },
            )
        }
    }

    private fun startProgressUpdates() {
        progressJob?.cancel()
        progressJob = scope.launch {
            while (currentCoroutineContext().isActive) {
                val controller = controller ?: break
                if (!controller.isPlaying) {
                    break
                }
                _state.update { value ->
                    value.copy(progress = controller.currentPosition)
                }
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