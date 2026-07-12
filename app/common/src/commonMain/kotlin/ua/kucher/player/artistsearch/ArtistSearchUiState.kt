package ua.kucher.player.artistsearch

import androidx.compose.runtime.Stable
import ua.kucher.player.common.ArtistUi

@Stable
internal data class ArtistSearchUiState(
    val searchQuery: String = "",
    val searchResult: List<ArtistUi> = emptyList(),
    val isPlayerShowed: Boolean = false,
)
