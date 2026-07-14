package ua.kucher.player.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.kucher.player.common.AlbumUi
import ua.kucher.player.theme.components.items.AlbumItem
import ua.kucher.player.theme.extensions.BottomNavSpacer

@Composable
internal fun AlbumsList(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    albums: List<AlbumUi> = emptyList(),
    onArtistClick: (id: Long) -> Unit,
    onMenuClick: (id: Long) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
    ) {
        items(
            items = albums,
            key = { album -> album.id },
            contentType = { "album" }
        ) { album ->
            AlbumItem(
                modifier = Modifier.fillMaxWidth(),
                title = album.title,
                numberOfSongs = album.numberOfSongs,
                artist = album.artistName,
                artwork = album.artwork ?: "",
                onMenuClick = {
                    onMenuClick(album.id)
                },
                onClick = {
                    onArtistClick(album.id)
                }
            )
        }
        item { BottomNavSpacer() }
    }
}