package ua.kucher.player.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.ic_pause_outline
import player.app.common.generated.resources.ic_repeat
import player.app.common.generated.resources.ic_shuffle
import player.app.common.generated.resources.ic_skip_back
import player.app.common.generated.resources.ic_skip_forward
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.items.PlayerMenuIconButton

@Composable
internal fun PlayerController(
    modifier: Modifier = Modifier,
    title: String,
    artist: String,
    onForward: () -> Unit,
    onPrevious: () -> Unit,
    onPlayPause: () -> Unit
) {
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
                .padding(horizontal = PlayerTheme.dimens.dimens8Px),
            text = title,
            color = PlayerTheme.colorScheme.primaryTextColor,
            style = PlayerTheme.typography.h4,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens8Px))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PlayerTheme.dimens.dimens8Px),
            text = artist,
            color = PlayerTheme.colorScheme.secondaryTextColor,
            style = PlayerTheme.typography.mediumTitle,
            maxLines = 1,
            overflow = TextOverflow.MiddleEllipsis
        )

        Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens32Px))

        var value by remember {
            mutableStateOf(0F)
        }

        Slider(
            modifier = Modifier
                .fillMaxWidth()
                .height(PlayerTheme.dimens.dimens16Px),
            value = value,
            onValueChange = { newValue ->
                value = newValue
            },
            valueRange = 0F..100F,
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
                text = "1:22",
                color = PlayerTheme.colorScheme.primaryTextColor,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "2:22",
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
                onClick = {
                    println("Shuffle")
                }
            )

            PlayerMenuIconButton(
                painter = painterResource(Res.drawable.ic_skip_back),
                onClick = onPrevious
            )

            Box(
                modifier = Modifier
                    .size(74.dp)
                    .clip(CircleShape)
                    .background(PlayerTheme.colorScheme.iconsMain.copy(alpha = 0.2F))
                    .clickable(onClick = onPlayPause),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_pause_outline),
                    colorFilter = ColorFilter.tint(PlayerTheme.colorScheme.iconsMain),
                    contentDescription = ""
                )
            }

            PlayerMenuIconButton(
                painter = painterResource(Res.drawable.ic_skip_forward),
                onClick = onForward
            )

            PlayerMenuIconButton(
                painter = painterResource(Res.drawable.ic_repeat),
                onClick = {
                    println("Repeat")
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
private fun PlayerControlPreview() {
    Box(modifier = Modifier.background(PlayerTheme.colorScheme.primaryBackground)) {
        PlayerController(
            title = "Title",
            artist = "Artist",
            onForward = {},
            onPrevious = {},
            onPlayPause = {}
        )
    }
}