package ua.kucher.player.playback

import android.content.Context
import androidx.media3.common.Player

internal class PlaybackAnalytics(
    private val context: Context
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

        if (currentItemId == null) return

        PlaybackAnalyticsWorker.start(
            context = context,
            songId = currentItemId!!,
            artistId = player.currentMediaItem?.artistId
        )
    }
}