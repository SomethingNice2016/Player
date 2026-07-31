package ua.kucher.player.artist.list

import androidx.compose.runtime.Immutable
import ua.kucher.player.common.ArtistUi

@Immutable
internal data class ArtistListUiState(
    val isRefreshing: Boolean = false,
    val artists: List<ArtistUi> = emptyList(),
)
