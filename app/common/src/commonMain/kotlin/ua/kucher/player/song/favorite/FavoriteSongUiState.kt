package ua.kucher.player.song.favorite

import ua.kucher.player.common.SongUi
import ua.kucher.player.theme.components.screentamplates.song.SongListUiState

internal data class FavoriteSongUiState(
    override val songs: List<SongUi> = emptyList(),
    override val playingSongId: Long? = null,
    override val isPlaying: Boolean = false,
    override val isRefreshing: Boolean = false
): SongListUiState