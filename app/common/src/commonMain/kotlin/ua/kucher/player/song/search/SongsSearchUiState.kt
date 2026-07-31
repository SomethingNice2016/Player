package ua.kucher.player.song.search

import androidx.compose.runtime.Immutable
import ua.kucher.player.common.SongUi

@Immutable
internal data class SongsSearchUiState(
    val searchQuery: String = "",
    val searchResult: List<SongUi> = emptyList(),
    val playingSongId: Long? = null,
    val isPlaying: Boolean = false,
)
