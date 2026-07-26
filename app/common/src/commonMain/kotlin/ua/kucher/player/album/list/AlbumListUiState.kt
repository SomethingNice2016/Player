package ua.kucher.player.album.list

import androidx.compose.runtime.Immutable
import ua.kucher.player.common.AlbumUi

@Immutable
internal data class AlbumListUiState(
    val isRefreshing: Boolean = false,
    val isPlayerShowed: Boolean = false,
    val albums: List<AlbumUi> = emptyList(),
)
