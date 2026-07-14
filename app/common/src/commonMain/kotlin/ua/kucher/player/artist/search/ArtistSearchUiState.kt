package ua.kucher.player.artist.search

import androidx.compose.runtime.Stable
import ua.kucher.player.common.ArtistUi

@Stable
internal data class ArtistSearchUiState(
    val searchQuery: String = "",
    val searchResult: List<ArtistUi> = emptyList(),
)
