package ua.kucher.player.artistlist

import androidx.compose.runtime.Stable
import ua.kucher.player.common.ArtistUi

@Stable
internal data class ArtistListUiState(
    val isRefreshing: Boolean = false,
    val isPlayerShowed: Boolean = false,
    val artists: List<ArtistUi> = emptyList(),
)
