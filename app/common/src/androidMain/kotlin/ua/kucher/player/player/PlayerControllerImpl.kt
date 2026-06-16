package ua.kucher.player.player

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ua.kucher.player.entity.Playlist
import ua.kucher.player.entity.PlaylistItem

internal class PlaybackControllerImpl : PlaybackController, Player.Listener {

    private val _currentItem = MutableStateFlow<PlaylistItem?>(null)

    private val _isPlaying = MutableStateFlow(false)

    private val _currentPlaylist = MutableStateFlow<Playlist?>(null)

    override val currentItem: Flow<PlaylistItem?>
        get() = _currentItem

    override val currentPlaylist: Flow<Playlist?>
        get() = _currentPlaylist

    override val isPlaying: Flow<Boolean>
        get() = _isPlaying


    private var controller: MediaController? = null

    fun setController(controller: MediaController?) {
        this.controller?.removeListener(this)
        this.controller = controller
        this.controller?.addListener(this)
    }

    override fun prepare(playlist: Playlist) = withController {
        if (playlist == _currentPlaylist.value) return@withController
        _currentPlaylist.value = playlist
        setMediaItems(playlist.items.map { it.toMediaItem() })
        prepare()
    }

    override fun play(item: PlaylistItem) = withController {
        if (currentMediaItem?.mediaId == item.id.toString()) {
            playPause()
            return@withController
        }

        val index = _currentPlaylist.value?.items?.indexOfFirst {
            it.id == item.id
        } ?: return@withController

        if (index == -1) return@withController

        seekToDefaultPosition(index)
        playWhenReady = true
    }

    override fun playPause() = withController {
        playWhenReady = !playWhenReady
    }

    override fun forward() {
        controller?.seekToNext()
    }

    override fun back() {
        controller?.seekToPrevious()
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        _isPlaying.value = isPlaying
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        _currentItem.value = _currentPlaylist.value?.items?.find { song ->
            song.id == mediaItem?.mediaId?.toLongOrNull()
        }
    }

    private fun withController(action: MediaController.() -> Unit) {
        controller?.let { nonNullController ->
            action.invoke(nonNullController)
        }
    }
}
