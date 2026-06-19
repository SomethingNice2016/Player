package ua.kucher.player.playback

import kotlinx.coroutines.flow.Flow
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

    val isPlaying: Flow<Boolean>

    val isShuffle: Flow<Boolean>

    val repeatMode: Flow<RepeatMode>

    val currentItemId: Flow<Long?>

    val nextItemId: Flow<Long?>

    val previousItemId: Flow<Long?>

    val progress: Flow<Long>

    fun prepare(playlist: Playlist)

    fun play(item: PlaylistItem)

    fun seekToPosition(position: Long)

    fun setShuffleMode(isShuffle: Boolean)

    fun setRepeatMode(mode: RepeatMode)

    fun playPause()

    fun forward()

    fun back()

}