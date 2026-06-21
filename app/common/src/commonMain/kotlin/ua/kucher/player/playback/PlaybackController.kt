package ua.kucher.player.playback

import kotlinx.coroutines.flow.StateFlow
import ua.kucher.player.entity.Playlist
import ua.kucher.player.entity.PlaylistItem

interface PlaybackController {

    enum class RepeatMode {
        OFF, ALL, ONE;

        companion object

        fun getNext() = when (this) {
            OFF -> ALL
            ALL -> ONE
            ONE -> OFF
        }
    }

    val state: StateFlow<PlaybackState>

    fun prepare(playlist: Playlist)

    fun play(item: PlaylistItem)

    fun seekToPosition(position: Long)

    fun setShuffleMode(isShuffle: Boolean)

    fun setRepeatMode(mode: RepeatMode)

    fun release()

    fun playPause()

    fun forward()

    fun back()

}