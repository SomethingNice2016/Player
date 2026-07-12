package ua.kucher.player.playback

import androidx.media3.common.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import ua.kucher.player.data.artist.ArtistRepository
import ua.kucher.player.data.song.SongRepository

internal class PlaybackAnalytics(
    private val songRepository: SongRepository,
    private val artistRepository: ArtistRepository,
    private val coroutineScope: CoroutineScope
) : Player.Listener {

    private var currentItemId: Long? = null

    override fun onEvents(player: Player, events: Player.Events) {
        dispatchCurrentItem(player)
    }

    private fun dispatchCurrentItem(player: Player) {
        val id = player.currentMediaItem
            ?.mediaId
            ?.toLongOrNull()

        if (id == currentItemId) return

        currentItemId = id

        id?.let { nonNullId ->
            coroutineScope.launch {
                songRepository.registerPlayback(nonNullId)
                songRepository.getSongById(nonNullId).firstOrNull()?.let { song ->
                    song.artist?.let { artist ->
                        artistRepository.incListenCount(artist.id)
                    }
                }
            }
        }
    }
}