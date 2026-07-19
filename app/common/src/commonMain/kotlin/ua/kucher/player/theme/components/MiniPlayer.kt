package ua.kucher.player.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.ic_pause_outline
import player.app.common.generated.resources.ic_play_outline
import player.app.common.generated.resources.ic_skip_back
import player.app.common.generated.resources.ic_skip_forward
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.items.PlayerMenuIconButton

@Composable
internal fun MiniPlayer(
    modifier: Modifier = Modifier,
    title: String,
    artist: String,
    progress: Long,
    duration: Long,
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onPrevious: () -> Unit,
    onForward: () -> Unit
) {

    val playButtonIconRes = if (isPlaying)
        Res.drawable.ic_pause_outline
    else
        Res.drawable.ic_play_outline


    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = PlayerTheme.dimens.dimens16Px)
                .padding(
                    top = PlayerTheme.dimens.dimens12Px,
                    bottom = PlayerTheme.dimens.dimens10Px
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(PlayerTheme.dimens.songIconSize))

            Spacer(Modifier.width(PlayerTheme.dimens.dimens16Px))

            Column(modifier = Modifier.weight(1F)) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .basicMarquee(
                            iterations = Int.MAX_VALUE,
                            initialDelayMillis = 0,
                            repeatDelayMillis = 0,
                            velocity = PlayerTheme.dimens.dimens32Px
                        ),
                    text = title,
                    color = PlayerTheme.colorScheme.primaryTextColor,
                    fontStyle = PlayerTheme.typography.largeBody.fontStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens2Px))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = artist,
                    color = PlayerTheme.colorScheme.secondaryTextColor,
                    fontStyle = PlayerTheme.typography.mediumBody.fontStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            PlayerMenuIconButton(
                painter = painterResource(Res.drawable.ic_skip_back),
                buttonSize = PlayerTheme.dimens.dimens40Px,
                contentDescription = "",
                onClick = onPrevious
            )

            Spacer(
                modifier = Modifier.width(
                    width = if (isPlaying)
                        PlayerTheme.dimens.dimens4Px
                    else
                        PlayerTheme.dimens.dimens6Px
                )
            )

            PlayerMenuIconButton(
                painter = painterResource(playButtonIconRes),
                buttonSize = PlayerTheme.dimens.dimens40Px,
                iconSize = PlayerTheme.dimens.dimens28Px,
                contentDescription = "",
                onClick = onPlayPause
            )

            Spacer(
                modifier = Modifier.width(
                    width = if (isPlaying)
                        PlayerTheme.dimens.dimens4Px
                    else
                        PlayerTheme.dimens.dimens2Px
                )
            )

            PlayerMenuIconButton(
                painter = painterResource(Res.drawable.ic_skip_forward),
                buttonSize = PlayerTheme.dimens.dimens40Px,
                contentDescription = "",
                onClick = onForward
            )
        }
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(PlayerTheme.dimens.dimens2Px),
            color = PlayerTheme.colorScheme.seekbarProgressColor,
            trackColor = Color.Transparent,
            gapSize = 0.dp,
            progress = { progress.toFloat() / duration.toFloat() },
            drawStopIndicator = {}
        )
    }
}

@Preview
@Composable
private fun MiniPlayerPreview() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(PlayerTheme.colorScheme.primaryBackground)
    ) {
        MiniPlayer(
            title = "Never fade away",
            artist = "SAMURAI",
            progress = 69L,
            duration = 228L,
            isPlaying = false,
            onPlayPause = {},
            onPrevious = {},
            onForward = {}
        )
    }
}