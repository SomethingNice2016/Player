package ua.kucher.player.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import ua.kucher.player.data.albun.AlbumRepository
import ua.kucher.player.data.artist.ArtistRepository
import ua.kucher.player.data.song.SongRepository

internal class HomeViewModel(
    private val songRepository: SongRepository,
    private val artistRepository: ArtistRepository,
    private val albumRepository: AlbumRepository
) : ViewModel() {

    val uiState = combine(
        songRepository.getSongsCount(),
        songRepository.getFavouriteSongsCount(),
        artistRepository.getArtistsCount(),
        albumRepository.getAlbumsCount(),
    ) { songsCount, favoriteSongsCount, artistsCount, albumCount ->
        HomeScreenUiState(
            songsCount = songsCount,
            favoriteSongsCount = favoriteSongsCount,
            artistsCount = artistsCount,
            albumCount = albumCount
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = HomeScreenUiState()
    )

}