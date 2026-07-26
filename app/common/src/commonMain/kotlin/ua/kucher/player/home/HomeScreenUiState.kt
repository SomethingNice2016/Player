package ua.kucher.player.home

import androidx.compose.runtime.Immutable
import ua.kucher.player.common.ArtistUi
import ua.kucher.player.common.SongUi

@Immutable
internal data class HomeScreenUiState(
    val songsCount: Int = 0,
    val favoriteSongsCount: Int = 0,
    val artistsCount: Int = 0,
    val albumCount: Int = 0,
    val playingSongId: Long? = null,
    val isPlaying: Boolean = false,
    val isRefreshing: Boolean = false,
    val topArtists: List<ArtistUi> = emptyList(),
    val recentlyPlayedSongs: List<SongUi> = emptyList()
)
