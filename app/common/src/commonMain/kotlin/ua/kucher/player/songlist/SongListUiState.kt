package ua.kucher.player.songlist

internal sealed class SongListUiState {

    data object Loading : SongListUiState()
    data object Error : SongListUiState()
    data class Success(val songs: List<SongUi>) : SongListUiState()

    companion object {
        fun success(songs: List<SongUi>): SongListUiState = Success(songs)
    }

}