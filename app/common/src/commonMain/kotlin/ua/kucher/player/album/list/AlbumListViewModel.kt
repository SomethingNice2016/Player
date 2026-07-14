package ua.kucher.player.album.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.kucher.player.common.AlbumUi
import ua.kucher.player.data.albun.AlbumRepository

internal class AlbumListViewModel(
    private val albumRepository: AlbumRepository,
): ViewModel() {

    private val isRefreshing = MutableStateFlow(false)

    val uiState = combine(
        isRefreshing,
        albumRepository.getAlbums()
    ) { isRefreshing, albums ->
        AlbumListUiState(
            isRefreshing = isRefreshing,
            albums = albums.map { album ->
                AlbumUi(
                    id = album.id,
                    title = album.title,
                    numberOfSongs = album.numberOfSongs,
                    artwork = album.artwork,
                    artistName = album.artist?.name ?: ""
                )
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = AlbumListUiState()
    )

    fun refresh() {
        viewModelScope.launch {
            isRefreshing.value = true
            albumRepository.fetchAlbums()
            isRefreshing.value = false
        }
    }
}