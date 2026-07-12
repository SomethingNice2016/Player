package ua.kucher.player.playback

import kotlinx.coroutines.flow.StateFlow
import ua.kucher.player.entity.PlaylistItem

internal class IOSPlaybackController : PlaybackController {

    override val state: StateFlow<PlaybackState>
        get() = TODO("Not yet implemented")

    override fun play(item: PlaylistItem) {
        TODO("Not yet implemented")
    }

    override fun play(playlist: List<PlaylistItem>, item: PlaylistItem) {
        TODO("Not yet implemented")
    }

    override fun playNext(item: PlaylistItem) {
        TODO("Not yet implemented")
    }

    override fun seekToPosition(position: Long) {
        TODO("Not yet implemented")
    }

    override fun setShuffleMode(isShuffle: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setRepeatMode(mode: PlaybackController.RepeatMode) {
        TODO("Not yet implemented")
    }

    override fun inQueue(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun release() {
        TODO("Not yet implemented")
    }

    override fun playPause() {
        TODO("Not yet implemented")
    }

    override fun forward() {
        TODO("Not yet implemented")
    }

    override fun back() {
        TODO("Not yet implemented")
    }

}