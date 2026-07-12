package ua.kucher.player.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.kucher.player.common.ArtistUi
import ua.kucher.player.theme.components.items.ArtistItem
import ua.kucher.player.theme.extensions.BottomNavSpacer
import ua.kucher.player.theme.extensions.MiniPlayerSpacer

@Composable
internal fun ArtistsList(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    artists: List<ArtistUi> = emptyList(),
    isPlayerShowed: Boolean = false,
    onArtistClick: (id: Long) -> Unit,
    onMenuClick: (id: Long) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
    ) {
        items(
            items = artists,
            key = { artist -> artist.id },
            contentType = { "artist" }
        ) { artist ->
            ArtistItem(
                modifier = Modifier.fillMaxWidth(),
                name = artist.name,
                numberOfSongs = artist.numberOfSongs,
                artwork = artist.artwork ?: "",
                onMenuClick = {
                    onMenuClick(artist.id)
                },
                onClick = {
                    onArtistClick(artist.id)
                }
            )
        }
        if (isPlayerShowed) {
            item { MiniPlayerSpacer() }
        }
        item { BottomNavSpacer() }
    }
}
