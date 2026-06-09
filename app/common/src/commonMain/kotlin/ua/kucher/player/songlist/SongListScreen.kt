package ua.kucher.player.songlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.painterResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.ic_options
import player.app.common.generated.resources.ic_play_background
import player.app.common.generated.resources.music_label
import ua.kucher.player.entity.Song
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.PlayerTopAppBar
import ua.kucher.player.theme.components.PlayerTopAppBarDefaults
import ua.kucher.player.theme.extensions.BottomNavSpacer

@Composable
internal fun SongListScreen(
    songs: List<Song>
) {

    val lazyListState = rememberLazyListState()

    val scrollBehavior = PlayerTopAppBarDefaults.scrollBehavior()

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = PlayerTheme.colorScheme.primaryBackground,
            topBar = {
                PlayerTopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    titleRes = Res.string.music_label,
                    navigationIcon = {},
                    showDivider = { false },
                    scrollBehavior = scrollBehavior
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                state = lazyListState,
            ) {
                itemsIndexed(
                    items = songs,
                    key = { index, song -> song.id + index }
                ) { _, song ->
                    SongItem(
                        modifier = Modifier.fillMaxWidth(),
                        title = song.title,
                        artist = song.artist?.name ?: "",
                        artwork = song.artwork
                    ) {

                    }
                }
                item {
                    BottomNavSpacer()
                }
            }
        }
        if (songs.isEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center),
                color = PlayerTheme.colorScheme.seekbarProgressColor,
                trackColor = PlayerTheme.colorScheme.seekbarProgressColor
            )
        }
    }
}

@Composable
private fun SongItem(
    modifier: Modifier = Modifier,
    title: String,
    artist: String,
    artwork: ByteArray?,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(
                top = PlayerTheme.dimens.dimens8Px,
                bottom = PlayerTheme.dimens.dimens8Px,
                start = PlayerTheme.dimens.dimens16Px
            )
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(PlayerTheme.dimens.songIconSize)
                .clip(PlayerTheme.shapes.radius4Px)
                .background(PlayerTheme.colorScheme.menuEnableButton.copy(alpha = 0.15F))
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberAsyncImagePainter(artwork),
                contentDescription = title,
                contentScale = ContentScale.Crop,
            )
            Icon(
                modifier = Modifier
                    .padding(PlayerTheme.dimens.dimens2Px)
                    .fillMaxSize(),
                painter = painterResource(Res.drawable.ic_play_background),
                tint = PlayerTheme.colorScheme.primaryTextColor,
                contentDescription = null,
            )
        }

        Spacer(modifier = Modifier.width(PlayerTheme.dimens.dimens16Px))
        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                color = PlayerTheme.colorScheme.primaryTextColor,
                fontStyle = PlayerTheme.typography.largeBody.fontStyle,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens2Px))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = artist,
                    color = PlayerTheme.colorScheme.secondaryTextColor,
                    fontStyle = PlayerTheme.typography.mediumBody.fontStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(PlayerTheme.dimens.dimens8Px))
                Text(
                    text = "*",
                    color = PlayerTheme.colorScheme.secondaryTextColor,
                    fontStyle = PlayerTheme.typography.mediumBody.fontStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(PlayerTheme.dimens.dimens8Px))
                Text(
                    text = "2:55",
                    color = PlayerTheme.colorScheme.secondaryTextColor,
                    fontStyle = PlayerTheme.typography.mediumBody.fontStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        IconButton(
            modifier = Modifier
                .size(PlayerTheme.dimens.menuIconSize)
                .padding(PlayerTheme.dimens.dimens12Px),
            onClick = {},
            content = {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(Res.drawable.ic_options),
                    contentDescription = null
                )
            }
        )
    }
}