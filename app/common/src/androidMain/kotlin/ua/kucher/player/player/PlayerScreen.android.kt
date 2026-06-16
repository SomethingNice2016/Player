@file:OptIn(ExperimentalMotionApi::class)

package ua.kucher.player.player

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import coil3.compose.AsyncImage
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.ic_arrow_down
import player.app.common.generated.resources.ic_cast
import ua.kucher.player.entity.PlaylistItem
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.AudioVisualizer
import ua.kucher.player.theme.components.FrostedGlass
import ua.kucher.player.theme.components.MiniPlayer
import ua.kucher.player.theme.components.PlayerController
import ua.kucher.player.theme.components.items.PlayerMenuIconButton
import ua.kucher.player.theme.extensions.BottomNavSpacer
import ua.kucher.player.theme.extensions.playerDragEvents


private const val BACKGROUND_REF = "playerBackground"
private const val MINI_PLAYER_REF = "miniPlayer"
private const val APPBAR_REF = "appbar"
private const val ARTWORK_REF = "bookBigImage"
private const val PLAYER_SHEET_REF = "playerSheet"
private const val SPACER_REF = "spacer"
private const val CONTENT_REF = "content"
private const val IMAGE_CORNER_RADIUS_REF = "image_corner_radius"
private const val BACKGROUND_CORNER_RADIUS_REF = "background_corner_radius"
private const val BACKGROUND_BORDER_ALPHA_REF = "background_border_alpha"
private const val VISUALIZER_FRACTION_COUNT = "visualizer_fraction_cont"
private const val VISUALIZER_PADDING = "visualizer_padding"


@OptIn(ExperimentalMotionApi::class)
@Composable
actual fun PlayerScreen(
    content: @Composable () -> Unit,
    item: PlaylistItem?,
    onForward: () -> Unit,
    onPrevious: () -> Unit,
    onPlayPause: () -> Unit

) {

    val motionScene = remember {
        motionScene()
    }

    val progress = remember {
        Animatable(0f)
    }

    val miniPlayerAlpha = when {
        progress.value <= 0.5F -> 1F - (progress.value / 0.5F)
        else -> 0f
    }

    val playerSheetAlpha = when {
        progress.value <= 0.5F -> 0F
        else -> (progress.value - 0.5F) / 0.5F

    }

    var job: Job? = remember { null }

    val scope = rememberCoroutineScope()

    MotionLayout(
        modifier = Modifier.fillMaxSize(),
        motionScene = motionScene,
        progress = progress.value
    ) {
        val artworkProperties = customProperties(ARTWORK_REF)
        val backgroundProperties = customProperties(BACKGROUND_REF)
        val backgroundShape = RoundedCornerShape(backgroundProperties.int(BACKGROUND_CORNER_RADIUS_REF).dp)
        val visualizerFractionCount = artworkProperties.int(VISUALIZER_FRACTION_COUNT)
        val visualizerPadding = artworkProperties.int(VISUALIZER_PADDING).dp

        Box(modifier = Modifier.layoutId(CONTENT_REF)) {
            content()
        }

        item?.let { nonNullItem ->
            Box(
                modifier = Modifier
                    .layoutId(BACKGROUND_REF)
                    .background(
                        color = PlayerTheme.colorScheme.primaryBackground,
                        shape = backgroundShape
                    )
                    .border(
                        shape = backgroundShape,
                        border = BorderStroke(
                            1.dp,
                            PlayerTheme.colorScheme.borderMain.copy(
                                alpha = backgroundProperties.float(BACKGROUND_BORDER_ALPHA_REF)
                            )
                        )
                    ).playerDragEvents(
                        onTap = {
                            scope.launch {
                                progress.animateTo(1F)
                            }
                        },
                        onVerticalDrag = { delta ->
                            job?.cancel()
                            job = scope.launch {
                                progress.snapTo((progress.value - delta).coerceIn(0F, 1F))
                            }
                        },
                        onVerticalDagEnd = {
                            scope.launch {
                                progress.animateTo(targetValue = if (progress.value > 0.5F) 1F else 0F)
                            }
                        }
                    )
            ) {
                FrostedGlass(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(backgroundShape),
                    blurRadius = 150F,
                    enabled = true,
                    tint = Color.Black.copy(alpha = 0.70F)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                            .clip(backgroundShape),
                        model = item.artwork,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Row(
                modifier = Modifier.layoutId(APPBAR_REF),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PlayerMenuIconButton(
                    painter = painterResource(Res.drawable.ic_arrow_down),
                    onClick = {
                        scope.launch {
                            progress.animateTo(0F)
                        }
                    }
                )
                PlayerMenuIconButton(
                    painter = painterResource(Res.drawable.ic_cast),
                    onClick = {}
                )
            }

            MiniPlayer(
                modifier = Modifier
                    .layoutId(MINI_PLAYER_REF)
                    .alpha(miniPlayerAlpha),
                artist = nonNullItem.artistTitle ?: "",
                title = nonNullItem.title
            )

            BottomNavSpacer(
                modifier = Modifier
                    .layoutId(SPACER_REF)
                    .padding(top = PlayerTheme.dimens.dimens8Px)
            )

            PlayerController(
                modifier = Modifier
                    .layoutId(PLAYER_SHEET_REF)
                    .alpha(playerSheetAlpha),
                title = item.title,
                artist = item.artistTitle ?: "",
                onForward = onForward,
                onPrevious = onPrevious,
                onPlayPause = onPlayPause
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .layoutId(ARTWORK_REF)
                    .clip(RoundedCornerShape(artworkProperties.int(IMAGE_CORNER_RADIUS_REF).dp)),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(artworkProperties.int(IMAGE_CORNER_RADIUS_REF).dp)),
                    model = nonNullItem.artwork,
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                AudioVisualizer(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(visualizerPadding),
                    isPlaying = true,
                    barsCount = visualizerFractionCount,
                    color = PlayerTheme.colorScheme.iconsMain
                )
            }
        }
    }
}

private fun motionScene() = MotionScene {

    val background = createRefFor(BACKGROUND_REF)
    val miniPlayer = createRefFor(MINI_PLAYER_REF)
    val appbar = createRefFor(APPBAR_REF)
    val artwork = createRefFor(ARTWORK_REF)
    val sheet = createRefFor(PLAYER_SHEET_REF)
    val spacer = createRefFor(SPACER_REF)
    val content = createRefFor(CONTENT_REF)

    val collapsedRef = constraintSet {
        constrain(content) {
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(background) {
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
            top.linkTo(miniPlayer.top)
            bottom.linkTo(miniPlayer.bottom)
            start.linkTo(miniPlayer.start)
            end.linkTo(miniPlayer.end)
            customInt(BACKGROUND_CORNER_RADIUS_REF, 24)
            customFloat(BACKGROUND_BORDER_ALPHA_REF, 0.25F)
        }

        constrain(miniPlayer) {
            width = Dimension.fillToConstraints
            bottom.linkTo(spacer.top)
            start.linkTo(
                anchor = parent.start,
                margin = 8.dp
            )
            end.linkTo(
                anchor = parent.end,
                margin = 8.dp
            )
        }

        constrain(spacer) {
            width = Dimension.fillToConstraints
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.start)
        }

        constrain(appbar) {
            width = Dimension.fillToConstraints
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            alpha = 0F
        }

        constrain(artwork) {
            width = Dimension.value(48.dp)
            height = Dimension.value(48.dp)
            start.linkTo(miniPlayer.start, 16.dp)
            top.linkTo(miniPlayer.top)
            bottom.linkTo(miniPlayer.bottom)
            alpha = 1F
            customInt(IMAGE_CORNER_RADIUS_REF, 8)
            customInt(VISUALIZER_FRACTION_COUNT, 5)
            customInt(VISUALIZER_PADDING, 8)
        }

        constrain(sheet) {
            width = Dimension.fillToConstraints
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(artwork.bottom)
        }
    }

    val expandedRef = constraintSet {
        constrain(content) {
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(background) {
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            customInt(BACKGROUND_CORNER_RADIUS_REF, 0)
            customFloat(BACKGROUND_BORDER_ALPHA_REF, 0F)
        }

        constrain(spacer) {
            width = Dimension.fillToConstraints
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(appbar) {
            width = Dimension.fillToConstraints
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            alpha = 1F
        }

        constrain(artwork) {
            width = Dimension.value(300.dp)
            height = Dimension.value(300.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            alpha = 1F
            top.linkTo(
                anchor = appbar.bottom,
                margin = 16.dp
            )
            customInt(IMAGE_CORNER_RADIUS_REF, 16)
            customInt(VISUALIZER_FRACTION_COUNT, 24)
            customInt(VISUALIZER_PADDING, 32)
        }

        constrain(sheet) {
            width = Dimension.fillToConstraints
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(artwork.bottom)
        }

        constrain(miniPlayer) {
            width = Dimension.fillToConstraints
            bottom.linkTo(spacer.top)
            start.linkTo(
                anchor = parent.start,
                margin = 8.dp
            )
            end.linkTo(
                anchor = parent.end,
                margin = 8.dp
            )
        }
    }

    defaultTransition(
        from = collapsedRef,
        to = expandedRef,
    )
}


@Preview
@Composable
private fun PlayerScreenPreview() {
    PlayerScreen(
        content = {},
        item = null,
        onForward = {},
        onPrevious = {},
        onPlayPause = {}
    )
}