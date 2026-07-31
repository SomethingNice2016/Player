package ua.kucher.player.album.detail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.default_song_artwork_mini
import player.app.common.generated.resources.ic_album
import player.app.common.generated.resources.ic_arrow_left
import player.app.common.generated.resources.ic_play_outline
import player.app.common.generated.resources.ic_shuffle
import player.app.common.generated.resources.play
import player.app.common.generated.resources.shuffle
import ua.kucher.player.common.AlbumUi
import ua.kucher.player.core.ui.utils.rememberStatusBarHeight
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.PlayerButton
import ua.kucher.player.theme.components.items.PlayerMenuIconButton
import ua.kucher.player.theme.components.items.SongItem
import ua.kucher.player.theme.extensions.BottomNavSpacer
import ua.kucher.player.theme.extensions.rememberImageRequest
import ua.kucher.player.theme.extensions.toPx


private const val ANIMATION_DURATION_MILLIS = 500

@Composable
internal fun AlbumDetailScreen(
    uiState: AlbumDetailUiState,
    onBackClick: () -> Unit,
    onSongClick: (id: Long) -> Unit,
    onMenuClick: (id: Long) -> Unit,
    onPlayClick: (isShuffle: Boolean) -> Unit
) {

    val artworkSize = 200.dp

    val artworkSizePixels = artworkSize.toPx()

    val placeholder = painterResource(Res.drawable.default_song_artwork_mini)

    val lazyListState = rememberLazyListState()

    val statusBarHeight = rememberStatusBarHeight()

    val appBarPadding = TopAppBarDefaults.LargeAppBarCollapsedHeight + statusBarHeight

    val scrollOffset = artworkSizePixels.toFloat()

    val appBarAlpha by remember {
        derivedStateOf {
            when {
                lazyListState.firstVisibleItemIndex > 1 -> 1F
                else -> (lazyListState.firstVisibleItemScrollOffset / scrollOffset)
                    .coerceIn(0F, 1F)
            }
        }
    }

    val appBarTextAlpha by animateFloatAsState(
        targetValue = if (appBarAlpha >= 0.95F) 1F else 0F,
        animationSpec = tween(ANIMATION_DURATION_MILLIS),
        label = "TopBarTitleAlpha"
    )

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            item {
                Spacer(modifier = Modifier.height(appBarPadding))
            }

            item {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(artworkSize)
                        .clip(PlayerTheme.shapes.radius12Px)
                        .background(PlayerTheme.colorScheme.rippleColor),
                    model = rememberImageRequest(
                        model = uiState.album?.artwork,
                        width = artworkSizePixels,
                        height = artworkSizePixels
                    ),
                    contentDescription = uiState.album?.title,
                    contentScale = ContentScale.Crop,
                    error = {
                        Image(
                            painter = painterResource(Res.drawable.ic_album),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(PlayerTheme.dimens.dimens36Px)
                        )
                    },
                )
            }

            item {
                Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens20Px))
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = PlayerTheme.dimens.dimens16Px),
                    horizontalArrangement = Arrangement.spacedBy(PlayerTheme.dimens.dimens16Px)
                ) {

                    PlayerButton(
                        modifier = Modifier.weight(1F),
                        text = stringResource(Res.string.play),
                        icon = vectorResource(Res.drawable.ic_play_outline),
                        onClick = {
                            onPlayClick(false)
                        }
                    )

                    PlayerButton(
                        modifier = Modifier.weight(1F),
                        text = stringResource(Res.string.shuffle),
                        icon = vectorResource(Res.drawable.ic_shuffle),
                        onClick = {
                            onPlayClick(true)
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens20Px))
            }

            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = PlayerTheme.dimens.dimens16Px),
                    text = uiState.album?.title.orEmpty(),
                    style = PlayerTheme.typography.h3,
                    color = PlayerTheme.colorScheme.primaryTextColor,
                    textAlign = TextAlign.Center
                )
            }

            item {
                Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens16Px))
            }

            items(
                items = uiState.songs,
                key = { item -> item.id }
            ) { item ->
                SongItem(
                    modifier = Modifier.fillMaxWidth(),
                    title = item.title,
                    artist = item.artistName,
                    duration = item.displayDuration,
                    isSongPlaying = uiState.playingItemId == item.id,
                    isPlaying = uiState.isPlaying,
                    artwork = item.artwork,
                    placeholder = placeholder,
                    onClick = { onSongClick(item.id) },
                    onMenuClick = { onMenuClick(item.id) }
                )
            }

            item { BottomNavSpacer() }
        }

        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = PlayerTheme.dimens.dimens16Px),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = PlayerTheme.colorScheme.primaryBackground.copy(alpha = appBarAlpha),
                titleContentColor = PlayerTheme.colorScheme.primaryTextColor,
                navigationIconContentColor = PlayerTheme.colorScheme.iconsMain,
                actionIconContentColor = PlayerTheme.colorScheme.iconsMain
            ),
            title = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(appBarTextAlpha)
                        .basicMarquee(
                            iterations = Int.MAX_VALUE,
                            initialDelayMillis = 0,
                            repeatDelayMillis = 0,
                            velocity = PlayerTheme.dimens.dimens32Px
                        ),
                    text = uiState.album?.title.orEmpty(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                PlayerMenuIconButton(
                    imageVector = vectorResource(Res.drawable.ic_arrow_left),
                    onClick = onBackClick
                )
            }
        )
    }
}

@Preview
@Composable
private fun AlbumDetailScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PlayerTheme.colorScheme.primaryBackground)
    ) {
        AlbumDetailScreen(
            uiState = AlbumDetailUiState(
                album = AlbumUi(
                    id = 1L,
                    title = "Newer fade away",
                    artwork = null,
                    artistName = "SAMURAI",
                    numberOfSongs = 2
                ),
                songs = emptyList()
            ),
            onBackClick = {},
            onSongClick = {},
            onMenuClick = {},
            onPlayClick = {}
        )
    }
}