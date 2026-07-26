package ua.kucher.player.common

import androidx.compose.runtime.Immutable
import ua.kucher.player.core.common.datetime.TimeFormatter
import ua.kucher.player.entity.Song

@Immutable
internal data class SongUi(
    val id: Long,
    val title: String,
    val artistName: String,
    val displayDuration: String,
    val duration: Long,
    val isFavorite: Boolean,
    val artwork: String?,
)

internal class SongUiMapper(
    private val timeFormatter: TimeFormatter,
) : Song.Mapper<SongUi> {

    override fun map(song: Song): SongUi {
        return SongUi(
            id = song.id,
            title = song.title,
            artwork = song.artwork,
            artistName = song.artist?.name ?: "",
            duration = song.duration,
            isFavorite = song.isFavorite,
            displayDuration = timeFormatter.formatDuration(song.duration),
        )
    }
}