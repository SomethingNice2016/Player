package ua.kucher.player.theme.components.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.default_song_artwork
import player.app.common.generated.resources.ic_options
import player.app.common.generated.resources.song_item_description
import ua.kucher.player.core.ui.components.AudioVisualizer
import ua.kucher.player.core.ui.components.FrostedGlass
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.extensions.rememberImageRequest
import ua.kucher.player.theme.extensions.toPx

@Composable
internal fun SongItem(
    modifier: Modifier = Modifier,
    title: String,
    artist: String,
    duration: String,
    isSongPlaying: Boolean,
    isPlaying: Boolean,
    artwork: String?,
    placeholder: Painter,
    onClick: () -> Unit,
    onMenuClick: () -> Unit
) {

    val backgroundColor = if (isSongPlaying)
        PlayerTheme.colorScheme.rippleColor
    else
        Color.Transparent

    val artworkSize = PlayerTheme.dimens.songIconSize
    val verticalPadding = PlayerTheme.dimens.dimens8Px
    val artworkPixelSize = artworkSize.toPx()

    val artworkImageRequest = rememberImageRequest(
        model = artwork,
        height = artworkPixelSize,
        width = artworkPixelSize
    )

    val artworkModifier = Modifier
        .size(artworkSize)
        .clip(PlayerTheme.shapes.radius4Px)
        .background(PlayerTheme.colorScheme.menuEnableButton.copy(alpha = 0.15F))

    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(backgroundColor)
            .padding(
                top = verticalPadding,
                bottom = verticalPadding,
                start = PlayerTheme.dimens.dimens16Px
            )
            .height(artworkSize)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (isSongPlaying) {
            Box(modifier = artworkModifier) {
                FrostedGlass(
                    modifier = Modifier.fillMaxSize(),
                    enabled = isSongPlaying,
                    tint = Color.Black.copy(alpha = 0.60F)
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = artworkImageRequest,
                        contentDescription = title,
                        contentScale = ContentScale.Crop,
                        error = placeholder,
                        placeholder = placeholder
                    )
                }
                AudioVisualizer(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(PlayerTheme.dimens.dimens8Px),
                    isPlaying = isPlaying,
                    barsCount = 5,
                    color = PlayerTheme.colorScheme.iconsMain
                )
            }
        } else {
            AsyncImage(
                modifier = artworkModifier,
                model = artworkImageRequest,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                error = placeholder,
            )
        }

        Column(
            modifier = Modifier
                .weight(1F)
                .padding(start = PlayerTheme.dimens.dimens16Px),
            verticalArrangement = Arrangement.spacedBy(PlayerTheme.dimens.dimens2Px),
        ) {
            Text(
                text = title,
                color = PlayerTheme.colorScheme.primaryTextColor,
                style = PlayerTheme.typography.mediumBody,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = stringResource(Res.string.song_item_description, artist, duration),
                color = PlayerTheme.colorScheme.secondaryTextColor,
                style = PlayerTheme.typography.smallBody,
                maxLines = 1,
                overflow = TextOverflow.MiddleEllipsis
            )
        }

        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = onMenuClick)
                .size(PlayerTheme.dimens.menuIconSize)
                .padding(PlayerTheme.dimens.dimens12Px),
            imageVector = vectorResource(Res.drawable.ic_options),
            contentDescription = null,
            tint = PlayerTheme.colorScheme.iconsMain
        )
    }
}

@Preview
@Composable
private fun SongItemPreview() {
    SongItem(
        modifier = Modifier.background(PlayerTheme.colorScheme.primaryBackground),
        title = "Naver fade away",
        artist = "SAMURAI",
        placeholder = painterResource(Res.drawable.default_song_artwork),
        duration = "2:22",
        isSongPlaying = false,
        isPlaying = false,
        artwork = "",
        onClick = {},
        onMenuClick = {},
    )
}