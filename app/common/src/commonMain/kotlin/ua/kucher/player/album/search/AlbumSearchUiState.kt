package ua.kucher.player.album.search

import androidx.compose.runtime.Immutable
import ua.kucher.player.common.AlbumUi

@Immutable
internal data class AlbumSearchUiState(
    val searchQuery: String = "",
    val searchResult: List<AlbumUi> = emptyList(),
)
