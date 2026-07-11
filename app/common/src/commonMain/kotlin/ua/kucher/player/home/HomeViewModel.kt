package ua.kucher.player.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import ua.kucher.player.common.ArtistUi
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
            albumCount = albumCount,
            topArtists = listOf(
                ArtistUi(
                    id = 1L,
                    name = "Samurai",
                    artwork = ""
                ),
                ArtistUi(
                    id = 2L,
                    name = "Samurai",
                    artwork = ""
                ),
                ArtistUi(
                    id = 3L,
                    name = "Samurai",
                    artwork = ""
                ),
                ArtistUi(
                    id = 4L,
                    name = "Samurai",
                    artwork = ""
                ),
                ArtistUi(
                    id = 5L,
                    name = "Samurai",
                    artwork = ""
                ),
                ArtistUi(
                    id = 6L,
                    name = "Samurai",
                    artwork = ""
                ),
                ArtistUi(
                    id = 7L,
                    name = "Samurai",
                    artwork = ""
                )
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = HomeScreenUiState()
    )

}