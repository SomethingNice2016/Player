package ua.kucher.player.songplayer

import androidx.compose.animation.core.Animatable
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
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.default_song_artwork
import player.app.common.generated.resources.ic_arrow_down
import player.app.common.generated.resources.ic_cast
import player.app.common.generated.resources.ic_options
import ua.kucher.player.common.SongUi
import ua.kucher.player.playback.PlaybackController
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.FrostedGlass
import ua.kucher.player.theme.components.MiniPlayer
import ua.kucher.player.theme.components.PlayerControl
import ua.kucher.player.theme.components.items.PlayerMenuIconButton
import ua.kucher.player.theme.extensions.bottomNavHeight
import ua.kucher.player.theme.extensions.playerDragEvents
import ua.kucher.player.theme.lerp
import ua.kucher.player.theme.rememberScreenSizeHeight
import ua.kucher.player.theme.rememberScreenSizeWidth
import ua.kucher.player.theme.rememberStatusBarHeight

private const val ANIMATION_DURATION_MILLIS = 500

@Composable
internal fun MusicPlayerScreen(
    modifier: Modifier = Modifier,
    state: MusicPlayerUiState?,
    onPlay: (Long) -> Unit,
    onForward: () -> Unit,
    onPrevious: () -> Unit,
    onPlayPause: () -> Unit,
    onShuffle: () -> Unit,
    onRepeat: () -> Unit,
    onSeek: (Long) -> Unit,
    content: @Composable () -> Unit,
) {

    val scope = rememberCoroutineScope()

    val screenWidth = rememberScreenSizeWidth()

    val statusBarHeight = rememberStatusBarHeight()

    val screenHeight = rememberScreenSizeHeight()

    val artworkSmallSize = PlayerTheme.dimens.songIconSize

    val artworkBigSize = screenWidth - (PlayerTheme.dimens.dimens24Px * 2)

    var job: Job? by remember {
        mutableStateOf(null)
    }

    val expandPlayerProgress = remember {
        Animatable(0F)
    }

    var dragPlayerStartProgress by remember {
        mutableFloatStateOf(0F)
    }

    var isPlayerExpanding by remember {
        mutableStateOf(false)
    }

    val backgroundHeight = lerp(
        start = artworkSmallSize + (PlayerTheme.dimens.dimens12Px * 2),
        stop = screenHeight + statusBarHeight,
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

    val imageBackgroundAlpha = expandPlayerProgress.value

    val appbarAlpha = playerSheetAlpha

    fun expandPlayer() {
        job?.cancel()
        job = scope.launch {
            expandPlayerProgress.animateTo(
                targetValue = 1F,
                animationSpec = tween(
                    durationMillis = ANIMATION_DURATION_MILLIS
                )
            )
        }
    }

    fun collapsePlayer() {
        job?.cancel()
        job = scope.launch {
            expandPlayerProgress.animateTo(
                targetValue = 0F,
                animationSpec = tween(
                    durationMillis = ANIMATION_DURATION_MILLIS
                )
            )
        }
    }


    Box(modifier = modifier.fillMaxSize()) {

        content()

        val pages = remember(state?.artworks) {
            state?.artworks?.entries?.sortedBy { it.key } ?: emptyList()
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

        LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
            if (pagerState.currentPage == currentIndex) return@LaunchedEffect
            if (pagerState.isScrollInProgress) return@LaunchedEffect
            onPlay(pages[pagerState.currentPage].key)
        }

        LaunchedEffect(currentIndex) {
            if (pagerState.currentPage != currentIndex) {
                pagerState.scrollToPage(currentIndex)
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
                        onTap = {
                            expandPlayer()
                        },
                        onVerticalDrag = { delta ->
                            job?.cancel()
                            job = scope.launch {
                                expandPlayerProgress.snapTo((expandPlayerProgress.value - delta).coerceIn(0f, 1F))
                            }
                        },
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

                println(nonNullState.currentSong.artwork)

                if (!nonNullState.currentSong.artwork.isNullOrBlank()) {
                    FrostedGlass(
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(imageBackgroundAlpha),
                        blurRadius = 150F,
                        enabled = true,
                        tint = Color.Black.copy(alpha = 0.70F)
                    ) {
                        AsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = nonNullState.currentSong.artwork,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
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
                        painter = painterResource(Res.drawable.ic_arrow_down),
                        onClick = {
                            collapsePlayer()
                        }
                    )

                    Spacer(Modifier.weight(1F))

                    PlayerMenuIconButton(
                        painter = painterResource(Res.drawable.ic_cast),
                        onClick = {}
                    )

                    PlayerMenuIconButton(
                        painter = painterResource(Res.drawable.ic_options),
                        onClick = {}
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
                        model = pages[page].value,
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(Res.drawable.default_song_artwork),
                        error = painterResource(Res.drawable.default_song_artwork)
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
                    onForward = onForward,
                    onPrevious = onPrevious,
                    onPlayPause = onPlayPause,
                    onShuffle = onShuffle,
                    onRepeat = onRepeat,
                    onSeek = onSeek
                )
            }
        }
    }
}

@Preview()
@Composable
private fun PlayerScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PlayerTheme.colorScheme.primaryBackground)
    ) {
        MusicPlayerScreen(
            Modifier.fillMaxSize(),
            state = MusicPlayerUiState(
                currentSong = SongUi(
                    id = 12,
                    title = "Never fade away",
                    artistName = "SAMURAI",
                    displayDuration = "3:33",
                    duration = 10000L,
                    artwork = ""
                ),
                displayProgress = "2:30",
                progress = 69000L,
                isPlaying = true,
                isShuffle = false,
                repeatMode = PlaybackController.RepeatMode.OFF,
                artworks = mapOf()
            ),
            onPlay = {},
            onForward = {},
            onPrevious = {},
            onPlayPause = {},
            onShuffle = {},
            onRepeat = {},
            onSeek = {},
            content = {}
        )
    }
}