package ua.kucher.player.album.list

import androidx.compose.runtime.Stable
import ua.kucher.player.common.AlbumUi

@Stable
internal data class AlbumListUiState(
    val isRefreshing: Boolean = false,
    val isPlayerShowed: Boolean = false,
    val albums: List<AlbumUi> = emptyList(),
)
