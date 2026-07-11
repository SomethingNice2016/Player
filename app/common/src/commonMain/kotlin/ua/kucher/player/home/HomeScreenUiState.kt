package ua.kucher.player.home

import androidx.compose.runtime.Stable

@Stable
data class HomeScreenUiState(
    val songsCount: Int = 0,
    val favoriteSongsCount: Int = 0,
    val artistsCount: Int = 0,
    val albumCount: Int = 0,
    val isRefreshing: Boolean = false,
)
