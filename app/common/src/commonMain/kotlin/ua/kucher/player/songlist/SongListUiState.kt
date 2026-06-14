package ua.kucher.player.songlist

import ua.kucher.player.songlist.SongListUiState.Success

internal sealed class SongListUiState {

    companion object {
        fun success(
            songs: List<SongUi>,
            playingSongId: Long?,
            isPlaying: Boolean
        ): SongListUiState = Success(
            songs = songs,
            playingSongId = playingSongId,
            isPlaying = isPlaying
        )
    }

    data object Loading : SongListUiState()
    data object Error : SongListUiState()
    data class Success(
        val songs: List<SongUi>,
        val playingSongId: Long?,
        val isPlaying: Boolean,
    ) : SongListUiState()

}

internal fun SongListUiState.onSuccess(action: (Success) -> Unit) {
    (this as? Success)?.let {
        action.invoke(this)
    }
}