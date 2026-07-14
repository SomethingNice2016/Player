package ua.kucher.player.theme.components.screentamplates.song

import ua.kucher.player.common.SongUi

internal interface SongListUiState {

    val songs: List<SongUi>

    val playingSongId: Long?

    val isPlaying: Boolean

    val isRefreshing: Boolean
}
