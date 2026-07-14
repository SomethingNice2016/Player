package ua.kucher.player.album.search

import androidx.compose.runtime.Stable
import ua.kucher.player.common.AlbumUi

@Stable
internal data class AlbumSearchUiState(
    val searchQuery: String = "",
    val searchResult: List<AlbumUi> = emptyList(),
)
