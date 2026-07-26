package ua.kucher.player.theme.components.items

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.default_song_artwork
import ua.kucher.player.core.ui.components.AudioVisualizer
import ua.kucher.player.core.ui.components.FrostedGlass
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.extensions.rememberImageRequest
import ua.kucher.player.theme.extensions.toPx

@Composable
internal fun SongGridItem(
    modifier: Modifier = Modifier,
    title: String,
    artist: String,
    isSongPlaying: Boolean,
    isPlaying: Boolean,
    artwork: String?,
    placeholder: Painter,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {

    val artworkSize = PlayerTheme.dimens.songIconSize

    val artworkSizePx = artworkSize.toPx()

    val artworkRequest = rememberImageRequest(
        uri = artwork,
        height = artworkSizePx,
        width = artworkSizePx
    )

    val artworkModifier = Modifier
        .size(PlayerTheme.dimens.songIconGridSize)
        .clip(PlayerTheme.shapes.radius4Px)
        .background(PlayerTheme.colorScheme.menuEnableButton.copy(alpha = 0.15F))

    Column(
        modifier = modifier
            .clip(PlayerTheme.shapes.radius4Px)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(PlayerTheme.dimens.dimens8Px)
            .width(PlayerTheme.dimens.songIconGridSize),
        verticalArrangement = Arrangement.spacedBy(PlayerTheme.dimens.dimens2Px)

    ) {
        if (isSongPlaying) {
            Box(modifier = artworkModifier) {
                FrostedGlass(
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.Black.copy(alpha = 0.60F)
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = artworkRequest,
                        contentDescription = title,
                        contentScale = ContentScale.Crop,
                        placeholder = placeholder,
                        error = placeholder
                    )
                }
                AudioVisualizer(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(PlayerTheme.dimens.dimens16Px),
                    isPlaying = isPlaying,
                    barsCount = 7,
                    color = PlayerTheme.colorScheme.iconsMain
                )
            }
        } else {
            AsyncImage(
                modifier = artworkModifier,
                model = artworkRequest,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                placeholder = placeholder,
                error = placeholder
            )
        }

        Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens2Px))

        Text(
            text = title,
            color = PlayerTheme.colorScheme.primaryTextColor,
            fontStyle = PlayerTheme.typography.largeBody.fontStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        
        Text(
            text = artist,
            color = PlayerTheme.colorScheme.secondaryTextColor,
            fontStyle = PlayerTheme.typography.mediumBody.fontStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun SongGridItemPreview() {
    Box(
        modifier = Modifier
            .clip(PlayerTheme.shapes.radius4Px)
            .background(PlayerTheme.colorScheme.primaryBackground)
    ) {
        SongGridItem(
            title = "Newer fade away",
            artist = "Samurai",
            placeholder = painterResource(Res.drawable.default_song_artwork),
            isSongPlaying = false,
            isPlaying = false,
            artwork = null,
            onClick = {},
            onLongClick = {}
        )
    }
}