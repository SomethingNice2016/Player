package ua.kucher.player.theme.components.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.album_item_description
import player.app.common.generated.resources.ic_album
import player.app.common.generated.resources.ic_options
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.extensions.toPx

@Composable
internal fun AlbumItem(
    modifier: Modifier = Modifier,
    title: String,
    artist: String,
    numberOfSongs: Int,
    artwork: String?,
    onClick: () -> Unit,
    onMenuClick: () -> Unit
) {

    val artworkSize = PlayerTheme.dimens.songIconSize

    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(
                top = PlayerTheme.dimens.dimens8Px,
                bottom = PlayerTheme.dimens.dimens8Px,
                start = PlayerTheme.dimens.dimens16Px
            )
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(artworkSize)
                .clip(PlayerTheme.shapes.radius4Px)
                .background(PlayerTheme.colorScheme.rippleColor),
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
            error = {
                Image(
                    painter = painterResource(Res.drawable.ic_album),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(PlayerTheme.dimens.dimens10Px)
                )
            }
        )

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
                text = stringResource(Res.string.album_item_description, artist, numberOfSongs),
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
internal fun AlbumItemPreview() {
    AlbumItem(
        modifier = Modifier.background(PlayerTheme.colorScheme.primaryBackground),
        title = "Never fade away",
        artist = "SAMURAI",
        artwork = "",
        numberOfSongs = 4,
        onClick = {},
        onMenuClick = {}
    )
}
