package ua.kucher.player.album.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ua.kucher.player.common.AlbumUi
import ua.kucher.player.data.albun.AlbumRepository

@OptIn(ExperimentalCoroutinesApi::class)
internal class AlbumSearchViewModel(
    private val albumRepository: AlbumRepository
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")

    private val searchResult = searchQuery.flatMapLatest { query ->
        albumRepository.searchAlbumsByTitle(query)
    }.map { albums ->
        albums.map { album ->
            AlbumUi(
                id = album.id,
                title = album.title,
                numberOfSongs = album.numberOfSongs,
                artwork = album.artwork,
                artistName = album.artist?.name ?: ""
            )
        }
    }

    val uiState = combine(
        searchQuery,
        searchResult
    ) { query, result ->
        AlbumSearchUiState(
            searchQuery = query,
            searchResult = result
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = AlbumSearchUiState()
    )

    fun search(query: String) {
        searchQuery.value = query
    }

}