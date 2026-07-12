package ua.kucher.player.artistlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.kucher.player.common.ArtistUi
import ua.kucher.player.data.artist.ArtistRepository
import ua.kucher.player.playback.PlaybackController


internal class ArtistListViewModel(
    private val artistRepository: ArtistRepository,
    private val playbackController: PlaybackController
) : ViewModel() {

    private val isRefreshing = MutableStateFlow(false)

    val uiState = combine(
        isRefreshing,
        playbackController.state,
        artistRepository.getArtists()
    ) { isRefreshing, playbackState, artists ->
        ArtistListUiState(
            isRefreshing = isRefreshing,
            isPlayerShowed = playbackState.currentItemId != null,
            artists = artists.map { artist ->
                ArtistUi(
                    id = artist.id,
                    name = artist.name,
                    numberOfSongs = artist.numberOfSongs,
                    artwork = ""
                )
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = ArtistListUiState()
    )

    fun refresh() {
        viewModelScope.launch {
            isRefreshing.value = true
            artistRepository.fetchArtists()
            isRefreshing.value = false
        }
    }
}