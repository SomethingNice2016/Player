package ua.kucher.player.playback

import androidx.media3.common.Player
import androidx.media3.session.MediaController
import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ua.kucher.player.entity.PlaylistItem
import kotlin.time.Duration.Companion.milliseconds

internal class AndroidPlaybackController : PlaybackController {

    companion object {
        private const val PROGRESS_UPDATE_DELAY = 200L
    }

    private var controller: MediaController? = null

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private var progressJob: Job? = null

    override val state: StateFlow<PlaybackState>
        field: MutableStateFlow<PlaybackState> = MutableStateFlow(PlaybackState())

    private val playerListener = object : Player.Listener {

        override fun onEvents(
            player: Player,
            events: Player.Events
        ) {
            handlePlaybackChanged(player, events)
            handleStateChanged(events)
        }

        private fun handlePlaybackChanged(
            player: Player,
            events: Player.Events
        ) {
            if (!events.contains(Player.EVENT_IS_PLAYING_CHANGED)) return

            if (player.isPlaying) {
                startProgressUpdates()
            } else {
                stopProgressUpdates()
            }
        }

        private fun handleStateChanged(
            events: Player.Events
        ) {
            if (
                events.contains(Player.EVENT_MEDIA_ITEM_TRANSITION) ||
                events.contains(Player.EVENT_PLAYBACK_STATE_CHANGED) ||
                events.contains(Player.EVENT_REPEAT_MODE_CHANGED) ||
                events.contains(Player.EVENT_SHUFFLE_MODE_ENABLED_CHANGED) ||
                events.contains(Player.EVENT_IS_PLAYING_CHANGED)
            ) {
                syncState()
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

    override fun play(playlist: List<PlaylistItem>, item: PlaylistItem) = withController {
        val mediaItems = playlist.map { playlistItem ->
            playlistItem.toMediaItem()
        }
        val index = mediaItems.indexOfFirst {
            it.mediaId == item.id.toString()
        }
        setMediaItems(mediaItems, index, 0L)
        prepare()
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
        state.update { value -> value.copy(progress = currentPosition) }
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
            state.update {
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
        state.update {
            PlaybackState(
                currentItemId = nonNullController.currentMediaItem?.mediaId?.toLongOrNull(),
                isPlaying = nonNullController.isPlaying,
                isShuffle = nonNullController.shuffleModeEnabled,
                repeatMode = PlaybackController.RepeatMode.fromPlayerRepeatMode(nonNullController.repeatMode),
                progress = nonNullController.currentPosition,
                artworks = buildMap { 
                    nonNullController.mediaItems.forEach { mediaItem ->
                        Logger.w(mediaItem.mediaMetadata.title.toString(), tag = "Player")
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
                state.update { value ->
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