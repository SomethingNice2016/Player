package ua.kucher.player.artist.search

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ua.kucher.player.common.toUi
import ua.kucher.player.core.common.presenter.Presenter
import ua.kucher.player.data.artist.ArtistRepository

@OptIn(ExperimentalCoroutinesApi::class)
internal class ArtistSearchPresenter(
    private val artistRepository: ArtistRepository,
    scope: CoroutineScope
) : Presenter(scope) {

    private val searchQuery = MutableStateFlow("")

    private val searchResult = searchQuery.flatMapLatest { query ->
        artistRepository.searchArtistsByName(query)
    }.map { artists ->
        artists.map { artist ->
            artist.toUi()
        }
    }

    val uiState = combine(
        searchQuery,
        searchResult,
    ) { query, result ->
        ArtistSearchUiState(
            searchQuery = query,
            searchResult = result,
        )
    }.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = ArtistSearchUiState()
    )


    fun search(query: String) {
        searchQuery.value = query
    }
}