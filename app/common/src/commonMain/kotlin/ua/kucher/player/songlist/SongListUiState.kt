package ua.kucher.player.songlist

import ua.kucher.player.common.SongUi

internal data class SongListUiState(
    val songs: List<SongUi> = emptyList(),
    val playingSongId: Long? = null,
    val isPlaying: Boolean = false,
    val isPlayerShowed: Boolean = false,
    val isRefreshing: Boolean = false
)