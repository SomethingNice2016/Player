package ua.kucher.player.theme.components.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.default_song_artwork
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.AudioVisualizer
import ua.kucher.player.theme.components.FrostedGlass

@Composable
internal fun SongGridItem(
    modifier: Modifier = Modifier,
    title: String,
    artist: String,
    isSongPlaying: Boolean,
    isPlaying: Boolean,
    artwork: String?,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .width(PlayerTheme.dimens.songIconGridSize)
            .clip(PlayerTheme.shapes.radius4Px)
            .clickable {
                onClick()
            }
    ) {
        Box(
            modifier = Modifier
                .size(PlayerTheme.dimens.songIconGridSize)
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
                    model = artwork,
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
                        .padding(PlayerTheme.dimens.dimens16Px),
                    isPlaying = isPlaying,
                    barsCount = 7,
                    color = PlayerTheme.colorScheme.iconsMain
                )
            }
        }

        Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens4Px))

        Text(
            text = title,
            color = PlayerTheme.colorScheme.primaryTextColor,
            fontStyle = PlayerTheme.typography.largeBody.fontStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens2Px))

        Text(
            text = artist,
            color = PlayerTheme.colorScheme.secondaryTextColor,
            fontStyle = PlayerTheme.typography.mediumBody.fontStyle,
            maxLines = 1,
            overflow = TextOverflow.MiddleEllipsis
        )
    }
}

@Preview
@Composable
private fun SongGridItemPreview() {
    SongGridItem(
        title = "Newer fade away",
        artist = "Samurai",
        isSongPlaying = false,
        isPlaying = false,
        artwork = null,
        onClick = {}
    )
}