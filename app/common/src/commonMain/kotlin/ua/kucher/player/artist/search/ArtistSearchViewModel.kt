package ua.kucher.player.artist.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ua.kucher.player.common.ArtistUi
import ua.kucher.player.data.artist.ArtistRepository

@OptIn(ExperimentalCoroutinesApi::class)
internal class ArtistSearchViewModel(
    private val artistRepository: ArtistRepository
) : ViewModel(){

    private val searchQuery = MutableStateFlow("")

    private val searchResult = searchQuery.flatMapLatest { query ->
        artistRepository.searchArtistsByName(query)
    }.map { artists ->
        artists.map { artist ->
            ArtistUi(
                id = artist.id,
                name = artist.name,
                numberOfSongs = artist.numberOfSongs,
                artwork = null,
            )
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
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = ArtistSearchUiState()
    )


    fun search(query: String) {
        searchQuery.value = query
    }
}