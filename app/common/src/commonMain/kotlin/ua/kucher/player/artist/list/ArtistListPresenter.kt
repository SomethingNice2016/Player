package ua.kucher.player.artist.list

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.kucher.player.common.toUi
import ua.kucher.player.core.ui.presenter.Presenter
import ua.kucher.player.data.artist.ArtistRepository


internal class ArtistListPresenter(
    private val artistRepository: ArtistRepository,
) : Presenter() {

    private val isRefreshing = MutableStateFlow(false)

    val uiState = combine(
        isRefreshing,
        artistRepository.getArtists()
    ) { isRefreshing, artists ->
        ArtistListUiState(
            isRefreshing = isRefreshing,
            artists = artists.map { artist ->
                artist.toUi()
            }
        )
    }.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = ArtistListUiState()
    )

    fun refresh() {
        scope.launch {
            isRefreshing.value = true
            artistRepository.fetchArtists()
            isRefreshing.value = false
        }
    }
}