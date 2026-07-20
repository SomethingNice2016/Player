package ua.kucher.player.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.ic_pause_outline
import player.app.common.generated.resources.ic_play_outline
import player.app.common.generated.resources.ic_repeat
import player.app.common.generated.resources.ic_repeat_one
import player.app.common.generated.resources.ic_shuffle
import player.app.common.generated.resources.ic_skip_back
import player.app.common.generated.resources.ic_skip_forward
import ua.kucher.player.playback.PlaybackController
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.items.PlayerMenuIconButton

private const val DIVIDER = 1000L

@Composable
internal fun PlayerControl(
    modifier: Modifier = Modifier,
    title: String,
    artist: String,
    displayDuration: String,
    displayProgress: String,
    duration: Long,
    progress: Long,
    isPlaying: Boolean,
    isShuffle: Boolean,
    repeatMode: PlaybackController.RepeatMode,
    onForward: () -> Unit,
    onPrevious: () -> Unit,
    onPlayPause: () -> Unit,
    onShuffle: () -> Unit,
    onRepeat: () -> Unit,
    onSeek: (newValue: Long) -> Unit,
    onTitleLongClick: () -> Unit,
    onArtistLongClick: () -> Unit,
) {

    var isDragging by remember { mutableStateOf(false) }

    var dragValue by remember { mutableFloatStateOf(0F) }

    val sliderValue = if (isDragging) dragValue else progress.div(DIVIDER).toFloat()

    val shuffleButtonColor = if (isShuffle)
        PlayerTheme.colorScheme.iconsMain
    else
        PlayerTheme.colorScheme.iconsMain.copy(alpha = 0.5F)

    val repeatButtonRes: DrawableResource
    val repeatButtonColor: Color


    when (repeatMode) {
        PlaybackController.RepeatMode.OFF -> {
            repeatButtonRes = Res.drawable.ic_repeat
            repeatButtonColor = PlayerTheme.colorScheme.iconsMain.copy(alpha = 0.5F)
        }

        PlaybackController.RepeatMode.ALL -> {
            repeatButtonRes = Res.drawable.ic_repeat
            repeatButtonColor = PlayerTheme.colorScheme.iconsMain
        }

        PlaybackController.RepeatMode.ONE -> {
            repeatButtonRes = Res.drawable.ic_repeat_one
            repeatButtonColor = PlayerTheme.colorScheme.iconsMain
        }
    }

    val titleInteractionSource = remember { MutableInteractionSource() }
    val artistInteractionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = PlayerTheme.dimens.dimens24Px,
                horizontal = PlayerTheme.dimens.dimens16Px
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = {},
                    onLongClick = onTitleLongClick,
                    interactionSource = titleInteractionSource,
                    indication = null,
                )
                .padding(horizontal = PlayerTheme.dimens.dimens8Px)
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    repeatDelayMillis = 0,
                    initialDelayMillis = 0,
                    velocity = PlayerTheme.dimens.dimens60Px
                ),
            text = title,
            color = PlayerTheme.colorScheme.primaryTextColor,
            style = PlayerTheme.typography.h4,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens8Px))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = {},
                    onLongClick = onArtistLongClick,
                    interactionSource = artistInteractionSource,
                    indication = null,
                )
                .padding(horizontal = PlayerTheme.dimens.dimens8Px),
            text = artist,
            color = PlayerTheme.colorScheme.secondaryTextColor,
            style = PlayerTheme.typography.mediumTitle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens32Px))

        Slider(
            modifier = Modifier
                .fillMaxWidth()
                .height(PlayerTheme.dimens.dimens16Px),
            value = sliderValue,
            valueRange = 0F..duration.div(DIVIDER).toFloat(),
            onValueChange = { newValue ->
                isDragging = true
                dragValue = newValue
            },
            onValueChangeFinished = {
                onSeek(dragValue.toLong().times(DIVIDER))
                isDragging = false
            },
            thumb = {
                Box(
                    modifier = Modifier
                        .size(PlayerTheme.dimens.seekbarThumbSize)
                        .background(
                            color = PlayerTheme.colorScheme.iconsMain,
                            shape = CircleShape
                        )
                )
            },
            track = { sliderState ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(PlayerTheme.dimens.seekbarTrackSize)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(PlayerTheme.colorScheme.seekbarColor)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(sliderState.coercedValueAsFraction)
                            .fillMaxHeight()
                            .clip(CircleShape)
                            .background(PlayerTheme.colorScheme.seekbarProgressColor)
                    )
                }
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PlayerTheme.dimens.dimens8Px),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = displayProgress,
                color = PlayerTheme.colorScheme.primaryTextColor,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = displayDuration,
                color = PlayerTheme.colorScheme.primaryTextColor,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PlayerMenuIconButton(
                painter = painterResource(Res.drawable.ic_shuffle),
                tint = shuffleButtonColor,
                onClick = onShuffle
            )

            PlayerMenuIconButton(
                painter = painterResource(Res.drawable.ic_skip_back),
                tint = PlayerTheme.colorScheme.iconsMain,
                iconSize = PlayerTheme.dimens.dimens32Px,
                onClick = onPrevious
            )

            val paddingVertical: Dp
            val paddingStart: Dp
            val paddingEnd: Dp

            if (isPlaying) {
                paddingVertical = PlayerTheme.dimens.dimens20Px
                paddingStart = PlayerTheme.dimens.dimens20Px
                paddingEnd = PlayerTheme.dimens.dimens20Px
            } else {
                paddingVertical = PlayerTheme.dimens.dimens20Px
                paddingStart = PlayerTheme.dimens.dimens24Px
                paddingEnd = PlayerTheme.dimens.dimens16Px
            }

            Box(
                modifier = Modifier
                    .size(74.dp)
                    .clip(CircleShape)
                    .background(PlayerTheme.colorScheme.iconsMain.copy(alpha = 0.2F))
                    .padding(
                        top = paddingVertical,
                        bottom = paddingVertical,
                        start = paddingStart,
                        end = paddingEnd
                    )
                    .clickable(onClick = onPlayPause),
                contentAlignment = Alignment.Center
            ) {
                val playButtonIconRes = if (isPlaying)
                    Res.drawable.ic_pause_outline
                else
                    Res.drawable.ic_play_outline

                Image(
                    painter = painterResource(playButtonIconRes),
                    colorFilter = ColorFilter.tint(PlayerTheme.colorScheme.iconsMain),
                    contentDescription = ""
                )
            }

            PlayerMenuIconButton(
                painter = painterResource(Res.drawable.ic_skip_forward),
                tint = PlayerTheme.colorScheme.iconsMain,
                iconSize = PlayerTheme.dimens.dimens32Px,
                onClick = onForward
            )

            PlayerMenuIconButton(
                painter = painterResource(repeatButtonRes),
                tint = repeatButtonColor,
                onClick = onRepeat
            )
        }
    }
}

@Preview
@Composable
private fun PlayerControlPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(PlayerTheme.colorScheme.primaryBackground)
    ) {
        PlayerControl(
            title = "Never fade away",
            artist = "SAMURAI",
            displayDuration = "2:28",
            displayProgress = "1:22",
            duration = 100000,
            progress = 69000,
            isPlaying = false,
            isShuffle = false,
            repeatMode = PlaybackController.RepeatMode.ONE,
            onForward = {},
            onPrevious = {},
            onPlayPause = {},
            onShuffle = {},
            onRepeat = {},
            onSeek = {},
            onTitleLongClick = {},
            onArtistLongClick = {}
        )
    }
}