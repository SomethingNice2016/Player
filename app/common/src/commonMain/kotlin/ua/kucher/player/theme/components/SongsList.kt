package ua.kucher.player.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.kucher.player.common.SongUi
import ua.kucher.player.theme.components.items.SongItem
import ua.kucher.player.theme.extensions.BottomNavSpacer
import ua.kucher.player.theme.extensions.MiniPlayerSpacer

@Composable
internal fun SongsList(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    onSongClick: (songId: Long) -> Unit,
    songs: List<SongUi> = emptyList(),
    playingSongId: Long? = null,
    isPlayerShowed: Boolean = false,
    isPlaying: Boolean = false,
) {
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
                artwork = song.artwork,
                duration = song.displayDuration,
                isSongPlaying = song.id == playingSongId,
                isPlaying = isPlaying,
                onClick = { onSongClick(song.id) }
            )
        }
        if (isPlayerShowed) {
            item { MiniPlayerSpacer() }
        }
        item { BottomNavSpacer() }
    }
}
