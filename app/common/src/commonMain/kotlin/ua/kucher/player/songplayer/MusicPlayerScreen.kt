package ua.kucher.player.songplayer

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.default_song_artwork
import player.app.common.generated.resources.ic_arrow_down
import player.app.common.generated.resources.ic_cast
import player.app.common.generated.resources.ic_options
import ua.kucher.player.common.SongUi
import ua.kucher.player.core.ui.components.FrostedGlass
import ua.kucher.player.core.ui.utils.lerp
import ua.kucher.player.core.ui.utils.rememberNavigationBarHeight
import ua.kucher.player.core.ui.utils.rememberScreenSizeHeight
import ua.kucher.player.core.ui.utils.rememberScreenSizeWidth
import ua.kucher.player.core.ui.utils.rememberStatusBarHeight
import ua.kucher.player.playback.PlaybackController
import ua.kucher.player.song.allsongs.AllSongScreen
import ua.kucher.player.song.allsongs.AllSongUiState
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.MiniPlayer
import ua.kucher.player.theme.components.PlayerControl
import ua.kucher.player.theme.components.items.PlayerMenuIconButton
import ua.kucher.player.theme.extensions.bottomNavHeight
import ua.kucher.player.theme.extensions.playerDragEvents
import ua.kucher.player.theme.extensions.rememberImageRequest
import ua.kucher.player.theme.extensions.toPx

@Composable
internal fun MusicPlayerScreen(
    modifier: Modifier = Modifier,
    expandPlayerProgress: Animatable<Float, AnimationVector1D>,
    state: MusicPlayerUiState?,
    onPlay: (Long) -> Unit,
    onForward: () -> Unit,
    onPrevious: () -> Unit,
    onPlayPause: () -> Unit,
    onShuffle: () -> Unit,
    onRepeat: () -> Unit,
    onSeek: (Long) -> Unit,
    expandPlayer: () -> Unit,
    collapsePlayer: () -> Unit,
    onVerticalDrag: (Float) -> Unit,
    onMenuClick: (Long) -> Unit,
    onTitleLongClick: () -> Unit,
    onArtistLongClick: () -> Unit,
    content: @Composable () -> Unit,
) {

    val screenWidth = rememberScreenSizeWidth()

    val statusBarHeight = rememberStatusBarHeight()

    val navBarHeight = rememberNavigationBarHeight()

    val screenHeight = rememberScreenSizeHeight()

    val artworkSmallSize = PlayerTheme.dimens.songIconSize

    val artworkBigSize = screenWidth - (PlayerTheme.dimens.dimens24Px * 2)

    val backgroundMaxHeight = screenHeight + statusBarHeight + navBarHeight

    val backgroundImagePixelSize = backgroundMaxHeight.toPx()

    var dragPlayerStartProgress by remember {
        mutableFloatStateOf(0F)
    }

    var isPlayerExpanding by remember {
        mutableStateOf(false)
    }

    val backgroundHeight = lerp(
        start = artworkSmallSize + (PlayerTheme.dimens.dimens12Px * 2),
        stop = screenHeight + statusBarHeight + navBarHeight,
        fraction = expandPlayerProgress.value
    )

    val backgroundBottomPadding = lerp(
        start = bottomNavHeight,
        stop = 0.dp,
        fraction = expandPlayerProgress.value
    )

    val artworkSize = lerp(
        start = artworkSmallSize,
        stop = artworkBigSize,
        fraction = expandPlayerProgress.value
    )

    val artworkPagerSize = lerp(
        start = artworkSmallSize + PlayerTheme.dimens.dimens16Px * 2,
        stop = screenWidth,
        fraction = expandPlayerProgress.value
    )

    val artworkCorner = lerp(
        start = PlayerTheme.dimens.dimens4Px,
        stop = PlayerTheme.dimens.dimens16Px,
        fraction = expandPlayerProgress.value
    )

    val artworkTopPadding = lerp(
        start = PlayerTheme.dimens.dimens12Px,
        stop = statusBarHeight + (PlayerTheme.dimens.dimens8Px * 2) + PlayerTheme.dimens.menuIconSize,
        fraction = expandPlayerProgress.value
    )

    val artworkStartPadding = lerp(
        start = PlayerTheme.dimens.dimens16Px,
        stop = (screenWidth / 2) - (artworkBigSize / 2),
        fraction = expandPlayerProgress.value
    )

    val miniPlayerAlpha = when {
        expandPlayerProgress.value <= 0.5F -> 1F - (expandPlayerProgress.value / 0.5F)
        else -> 0F
    }

    val playerSheetAlpha = when {
        expandPlayerProgress.value <= 0.7F -> 0F
        expandPlayerProgress.value >= 1F -> 1F
        else -> (expandPlayerProgress.value - 0.7F) / 0.3F
    }

    val artworkPixelSize = artworkBigSize.toPx()

    val imageBackgroundAlpha = expandPlayerProgress.value

    val appbarAlpha = playerSheetAlpha

    val placeholder = painterResource(Res.drawable.default_song_artwork)

    Box(modifier = modifier.fillMaxSize()) {

        content()

        val pages = remember(state?.artworks?.keys?.toList()) {
            state?.artworks?.entries?.toList() ?: emptyList()
        }

        val currentIndexMap: Map<Long, Int> = remember(pages) {
            pages.mapIndexed { index, item ->
                item.key to index
            }.toMap()
        }

        val currentIndex = state?.currentSong?.id?.let { nonNullId ->
            currentIndexMap[nonNullId]
        } ?: 0

        val pagerState = rememberPagerState(
            pageCount = { pages.size },
        )

        LaunchedEffect(currentIndex) {
            if (pagerState.currentPage != currentIndex) {
                pagerState.scrollToPage(currentIndex)
            }
        }

        LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
            if (pagerState.currentPage == currentIndex) return@LaunchedEffect
            if (pagerState.isScrollInProgress) return@LaunchedEffect
            pages.getOrNull(pagerState.currentPage)?.let { entry ->
                onPlay(entry.key)
            }
        }

        state?.let { nonNullState ->
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = backgroundBottomPadding)
                    .height(backgroundHeight)
                    .clip(RectangleShape)
                    .background(PlayerTheme.colorScheme.secondaryBackground)
                    .playerDragEvents(
                        onVerticalDrag = onVerticalDrag,
                        onTap = expandPlayer,
                        onVerticalDagStart = {
                            dragPlayerStartProgress = expandPlayerProgress.value
                        },
                        onVerticalDagEnd = {
                            isPlayerExpanding = expandPlayerProgress.value > dragPlayerStartProgress
                            when {
                                isPlayerExpanding -> if (expandPlayerProgress.value >= 0.15F)
                                    expandPlayer()
                                else
                                    collapsePlayer()

                                else -> if (expandPlayerProgress.value <= 0.85F)
                                    collapsePlayer()
                                else
                                    expandPlayer()
                            }
                        }
                    )
            ) {
                Crossfade(
                    targetState = nonNullState.currentSong,
                    label = "Artwork",
                    animationSpec = tween(
                        durationMillis = 1500,
                    )
                ) {
                    FrostedGlass(
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(imageBackgroundAlpha),
                        blurRadius = 90F,
                        enabled = true,
                        tint = Color.Black.copy(alpha = 0.7F)
                    ) {
                        AsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = rememberImageRequest(
                                model = nonNullState.currentSong.artwork,
                                height = backgroundImagePixelSize,
                                width = backgroundImagePixelSize,
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(
                            horizontal = PlayerTheme.dimens.dimens16Px,
                            vertical = PlayerTheme.dimens.dimens8Px
                        )
                        .alpha(appbarAlpha),
                ) {
                    PlayerMenuIconButton(
                        imageVector = vectorResource(Res.drawable.ic_arrow_down),
                        onClick = {
                            collapsePlayer()
                        }
                    )

                    Spacer(Modifier.weight(1F))

                    PlayerMenuIconButton(
                        imageVector = vectorResource(Res.drawable.ic_cast),
                        onClick = {}
                    )

                    PlayerMenuIconButton(
                        imageVector = vectorResource(Res.drawable.ic_options),
                        onClick = {
                            onMenuClick(nonNullState.currentSong.id)
                        }
                    )
                }

                HorizontalPager(
                    modifier = Modifier
                        .padding(top = artworkTopPadding)
                        .width(artworkPagerSize)
                        .height(artworkSize),
                    state = pagerState,
                    userScrollEnabled = expandPlayerProgress.value >= 1F,
                    pageSpacing = artworkStartPadding,
                    contentPadding = PaddingValues(horizontal = artworkStartPadding),
                ) { page ->
                    AsyncImage(
                        modifier = Modifier
                            .size(artworkSize)
                            .clip(RoundedCornerShape(artworkCorner)),
                        model = rememberImageRequest(
                            model = pages[page].value,
                            height = artworkPixelSize,
                            width = artworkPixelSize,
                        ),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        placeholder = placeholder,
                        error = placeholder
                    )
                }

                MiniPlayer(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .alpha(miniPlayerAlpha),
                    artist = nonNullState.currentSong.artistName,
                    title = nonNullState.currentSong.title,
                    progress = nonNullState.progress,
                    duration = nonNullState.currentSong.duration,
                    isPlaying = nonNullState.isPlaying,
                    onPlayPause = onPlayPause,
                    onPrevious = onPrevious,
                    onForward = onForward
                )

                PlayerControl(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = artworkTopPadding + artworkSize + PlayerTheme.dimens.dimens24Px)
                        .alpha(playerSheetAlpha),
                    title = nonNullState.currentSong.title,
                    artist = nonNullState.currentSong.artistName,
                    displayDuration = nonNullState.currentSong.displayDuration,
                    displayProgress = nonNullState.displayProgress,
                    progress = nonNullState.progress,
                    duration = nonNullState.currentSong.duration,
                    isPlaying = nonNullState.isPlaying,
                    isShuffle = nonNullState.isShuffle,
                    repeatMode = nonNullState.repeatMode,
                    onTitleLongClick = onTitleLongClick,
                    onArtistLongClick = onArtistLongClick,
                    onForward = onForward,
                    onPrevious = onPrevious,
                    onPlayPause = onPlayPause,
                    onShuffle = onShuffle,
                    onRepeat = onRepeat,
                    onSeek = onSeek,
                )
            }
        }
    }
}

@Preview()
@Composable
private fun PlayerScreenPreviewAll() {
    val songUi = SongUi(
        id = 12,
        title = "Never fade away",
        artistName = "SAMURAI",
        displayDuration = "3:33",
        duration = 69000L,
        artwork = "",
        isFavorite = false
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PlayerTheme.colorScheme.primaryBackground)
    ) {
        MusicPlayerScreen(
            Modifier.fillMaxSize(),
            state = MusicPlayerUiState(
                currentSong = songUi,
                displayProgress = "2:30",
                progress = 20000,
                isPlaying = true,
                isShuffle = false,
                repeatMode = PlaybackController.RepeatMode.OFF,
                artworks = mapOf(12L to "")
            ),
            onPlay = {},
            onForward = {},
            onPrevious = {},
            onPlayPause = {},
            onShuffle = {},
            onRepeat = {},
            onSeek = {},
            onTitleLongClick = {},
            onArtistLongClick = {},
            onVerticalDrag = {},
            expandPlayerProgress = Animatable(1F),
            expandPlayer = {},
            collapsePlayer = {},
            onMenuClick = {},
            content = {
                AllSongScreen(
                    uiState = AllSongUiState(
                        songs = listOf(
                            songUi,
                            songUi.copy(id = 13),
                            songUi.copy(id = 14),
                            songUi.copy(id = 15),
                        ),
                        isPlaying = true,
                        playingSongId = 12L
                    ),
                    onSongClick = {},
                    onRefresh = {},
                    onSearch = {},
                    onMenuClick = {},
                )
            }
        )
    }
}