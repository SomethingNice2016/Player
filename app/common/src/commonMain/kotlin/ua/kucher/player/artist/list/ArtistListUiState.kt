package ua.kucher.player.artist.list

import androidx.compose.runtime.Stable
import ua.kucher.player.common.ArtistUi

@Stable
internal data class ArtistListUiState(
    val isRefreshing: Boolean = false,
    val artists: List<ArtistUi> = emptyList(),
)
