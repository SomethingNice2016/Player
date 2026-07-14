package ua.kucher.player.album.list

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.kucher.player.common.toUi
import ua.kucher.player.core.ui.presenter.Presenter
import ua.kucher.player.data.albun.AlbumRepository

internal class AlbumListPresenter(
    private val albumRepository: AlbumRepository,
    scope: CoroutineScope
) : Presenter(scope) {

    private val isRefreshing = MutableStateFlow(false)

    val uiState = combine(
        isRefreshing,
        albumRepository.getAlbums()
    ) { isRefreshing, albums ->
        AlbumListUiState(
            isRefreshing = isRefreshing,
            albums = albums.map { album ->
                album.toUi()
            }
        )
    }.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = AlbumListUiState()
    )

    fun refresh() {
        scope.launch {
            isRefreshing.value = true
            albumRepository.fetchAlbums()
            isRefreshing.value = false
        }
    }
}