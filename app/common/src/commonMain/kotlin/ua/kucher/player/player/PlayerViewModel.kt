package ua.kucher.player.player

import androidx.lifecycle.ViewModel


internal class PlayerViewModel(
    private val playbackController: PlaybackController
) : ViewModel() {

    val item = playbackController.currentItem

    fun forward() {
        playbackController.forward()
    }

    fun back() {
        playbackController.back()
    }

    fun playPause() {
        playbackController.playPause()
    }

}