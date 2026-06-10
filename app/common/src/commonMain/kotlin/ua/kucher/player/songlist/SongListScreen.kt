package ua.kucher.player.songlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
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
import ua.kucher.player.core.common.bitmap.SharedBitmap
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.PlayerTopAppBar
import ua.kucher.player.theme.components.PlayerTopAppBarDefaults
import ua.kucher.player.theme.extensions.BottomNavSpacer


private const val SONG_IMAGE_QUALITY = 25
@Composable
internal fun SongListScreen(
    uiState: SongListUiState,
    onSongClick: (songId: Long) -> Unit
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
            when (uiState) {
                SongListUiState.Error -> {}
                SongListUiState.Loading -> CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center),
                    color = PlayerTheme.colorScheme.seekbarProgressColor,
                    trackColor = PlayerTheme.colorScheme.seekbarProgressColor
                )

                is SongListUiState.Success -> SuccessContent(
                    modifier = Modifier.padding(paddingValues),
                    uiState = uiState,
                    lazyListState = lazyListState,
                    onSongClick = onSongClick
                )
            }
        }
    }
}

@Composable
private fun SuccessContent(
    modifier: Modifier = Modifier,
    uiState: SongListUiState.Success,
    lazyListState: LazyListState,
    onSongClick: (songId: Long) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
    ) {
        items(
            items = uiState.songs,
            key = { song -> song.id }
        ) { song ->
            SongItem(
                modifier = Modifier.fillMaxWidth(),
                title = song.title,
                artist = song.artistName,
                artwork = song.artwork,
                duration = song.duration,
                onClick = { onSongClick(song.id) }
            )
        }
        item {
            BottomNavSpacer()
        }
    }
}

@Composable
private fun SongItem(
    modifier: Modifier = Modifier,
    title: String,
    artist: String,
    duration: String,
    artwork: SharedBitmap?,
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
                painter = rememberAsyncImagePainter(artwork?.toByteArray(SONG_IMAGE_QUALITY)),
                contentDescription = title,
                contentScale = ContentScale.Crop,
            )
            Icon(
                modifier = Modifier
                    .padding(PlayerTheme.dimens.dimens2Px)
                    .fillMaxSize(),
                painter = painterResource(Res.drawable.ic_play_background),
                tint = PlayerTheme.colorScheme.iconsMain,
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
                    text = duration,
                    color = PlayerTheme.colorScheme.secondaryTextColor,
                    fontStyle = PlayerTheme.typography.mediumBody.fontStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        IconButton(
            modifier = Modifier.size(PlayerTheme.dimens.menuIconSize),
            onClick = {},
            content = {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(PlayerTheme.dimens.dimens12Px),
                    painter = painterResource(Res.drawable.ic_options),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(PlayerTheme.colorScheme.iconsMain)
                )
            }
        )
    }
}