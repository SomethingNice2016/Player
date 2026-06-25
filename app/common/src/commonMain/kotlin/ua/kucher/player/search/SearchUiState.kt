package ua.kucher.player.search

import androidx.compose.runtime.Stable
import ua.kucher.player.common.SongUi

@Stable
internal data class SearchUiState(
    val searchQuery: String = "",
    val searchResult: List<SongUi> = emptyList(),
    val playingSongId: Long? = null,
    val isPlaying: Boolean = false,
    val isPlayerShowed: Boolean = false,
)
