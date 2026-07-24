package ua.kucher.player.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.default_song_artwork
import ua.kucher.player.common.SongUi
import ua.kucher.player.theme.components.items.SongItem
import ua.kucher.player.theme.extensions.BottomNavSpacer

@Composable
internal fun SongsList(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    onSongClick: (id: Long) -> Unit,
    onMenuClick: (id: Long) -> Unit,
    songs: List<SongUi> = emptyList(),
    playingSongId: Long? = null,
    isPlaying: Boolean = false,
) {

    val placeholder = painterResource(Res.drawable.default_song_artwork)

    LazyColumn(
        modifier = modifier,
        state = lazyListState,
    ) {
        items(
            items = songs,
            key = { song -> song.id },
            contentType = { "song" }
        ) { song ->
            SongItem(
                modifier = Modifier.fillMaxWidth(),
                title = song.title,
                artist = song.artistName,
                placeholder = placeholder,
                artwork = song.artwork,
                duration = song.displayDuration,
                isSongPlaying = song.id == playingSongId,
                isPlaying = isPlaying,
                onClick = { onSongClick(song.id) },
                onMenuClick = { onMenuClick(song.id) }
            )
        }
        item { BottomNavSpacer() }
    }
}
