package ua.kucher.player.album.search

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
import ua.kucher.player.data.albun.AlbumRepository

@OptIn(ExperimentalCoroutinesApi::class)
internal class AlbumSearchPresenter(
    private val albumRepository: AlbumRepository,
    scope: CoroutineScope
) : Presenter(scope) {

    private val searchQuery = MutableStateFlow("")

    private val searchResult = searchQuery.flatMapLatest { query ->
        albumRepository.searchAlbumsByTitle(query)
    }.map { albums ->
        albums.map { album ->
            album.toUi()
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
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = AlbumSearchUiState()
    )

    fun search(query: String) {
        searchQuery.value = query
    }

}