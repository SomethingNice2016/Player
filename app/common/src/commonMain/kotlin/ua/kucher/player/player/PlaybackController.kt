package ua.kucher.player.player

import kotlinx.coroutines.flow.Flow
import ua.kucher.player.entity.Playlist
import ua.kucher.player.entity.PlaylistItem

interface PlaybackController {

    val isPlaying: Flow<Boolean>

    val currentItem: Flow<PlaylistItem?>

    val currentPlaylist: Flow<Playlist?>

    fun prepare(playlist: Playlist)

    fun play(item: PlaylistItem)

    fun playPause()

    fun forward()

    fun back()

}