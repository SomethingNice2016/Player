package ua.kucher.player.artist.search

import androidx.compose.runtime.Immutable
import ua.kucher.player.common.ArtistUi

@Immutable
internal data class ArtistSearchUiState(
    val searchQuery: String = "",
    val searchResult: List<ArtistUi> = emptyList(),
)
