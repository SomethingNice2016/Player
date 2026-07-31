package ua.kucher.player.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.ic_artist
import ua.kucher.player.common.ArtistUi
import ua.kucher.player.theme.components.items.ArtistItem
import ua.kucher.player.theme.extensions.BottomNavSpacer

@Composable
internal fun ArtistsList(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    artists: List<ArtistUi> = emptyList(),
    onArtistClick: (id: Long) -> Unit,
    onMenuClick: (id: Long) -> Unit
) {

    val placeholder = painterResource(Res.drawable.ic_artist)

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
                numberOfAlbums = artist.numberOfAlbums,
                placeholder = placeholder,
                artwork = artist.artwork.orEmpty(),
                onMenuClick = {
                    onMenuClick(artist.id)
                },
                onClick = {
                    onArtistClick(artist.id)
                }
            )
        }
        item { BottomNavSpacer() }
    }
}
