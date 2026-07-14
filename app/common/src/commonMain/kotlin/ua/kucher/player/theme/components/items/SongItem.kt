package ua.kucher.player.theme.components.items

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.default_song_artwork
import player.app.common.generated.resources.ic_options
import player.app.common.generated.resources.song_item_description
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.AudioVisualizer
import ua.kucher.player.theme.components.FrostedGlass
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
    onClick: () -> Unit,
    onMenuClick: () -> Unit
) {

    val backgroundColor = if (isSongPlaying)
        PlayerTheme.colorScheme.rippleColor
    else
        Color.Transparent

    val artworkSize = PlayerTheme.dimens.songIconSize

    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(backgroundColor)
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
                .size(artworkSize)
                .clip(PlayerTheme.shapes.radius4Px)
                .background(PlayerTheme.colorScheme.menuEnableButton.copy(alpha = 0.15F))
        ) {
            FrostedGlass(
                modifier = Modifier.fillMaxSize(),
                enabled = isSongPlaying,
                tint = Color.Black.copy(alpha = 0.60F)
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(artwork)
                        .size(
                            width = artworkSize.toPx(),
                            height = artworkSize.toPx()
                        )
                        .crossfade(true)
                        .build(),
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(Res.drawable.default_song_artwork),
                    error = painterResource(Res.drawable.default_song_artwork)
                )
            }
            if (isSongPlaying) {
                AudioVisualizer(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(PlayerTheme.dimens.dimens8Px),
                    isPlaying = isPlaying,
                    barsCount = 5,
                    color = PlayerTheme.colorScheme.iconsMain
                )
            }
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

            Text(
                text = stringResource(Res.string.song_item_description, artist, duration),
                color = PlayerTheme.colorScheme.secondaryTextColor,
                fontStyle = PlayerTheme.typography.mediumBody.fontStyle,
                maxLines = 1,
                overflow = TextOverflow.MiddleEllipsis
            )
        }
        IconButton(
            modifier = Modifier.size(PlayerTheme.dimens.menuIconSize),
            onClick = onMenuClick,
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

@Preview
@Composable
private fun SongItemPreview() {
    SongItem(
        modifier = Modifier.background(PlayerTheme.colorScheme.primaryBackground),
        title = "Naver fade away",
        artist = "SAMURAI",
        duration = "2:22",
        isSongPlaying = false,
        isPlaying = false,
        artwork = "",
        onClick = {},
        onMenuClick = {}
    )
}