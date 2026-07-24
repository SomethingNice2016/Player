package ua.kucher.player.playback

data class PlaybackState(
    val artworks: Map<Long, String?> = emptyMap(),
    val currentItemId: Long? = null,
    val isPlaying: Boolean = false,
    val isShuffle: Boolean = false,
    val repeatMode: PlaybackController.RepeatMode = PlaybackController.RepeatMode.OFF,
    val progress: Long = 0L
)