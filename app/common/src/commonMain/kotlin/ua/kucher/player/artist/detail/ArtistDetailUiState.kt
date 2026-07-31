package ua.kucher.player.artist.detail

import androidx.compose.runtime.Immutable
import ua.kucher.player.common.AlbumUi
import ua.kucher.player.common.ArtistUi
import ua.kucher.player.common.SongUi

@Immutable
internal data class ArtistDetailUiState(
    val artist: ArtistUi? = null,
    val songs: List<SongUi> = emptyList(),
    val albums: List<AlbumUi> = emptyList()
)
