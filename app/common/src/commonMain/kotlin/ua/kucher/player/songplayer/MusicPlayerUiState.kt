package ua.kucher.player.songplayer

import androidx.compose.runtime.Stable
import ua.kucher.player.SongUi
import ua.kucher.player.playback.PlaybackController

@Stable
internal data class MusicPlayerUiState(
    val currentSong: SongUi,
    val previousSong: SongUi?,
    val nextSong: SongUi?,
    val displayProgress: String,
    val progress: Long,
    val isPlaying: Boolean,
    val isShuffle: Boolean,
    val repeatMode: PlaybackController.RepeatMode
)