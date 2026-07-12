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
import androidx.compose.foundation.shape.CircleShape
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
import coil3.compose.SubcomposeAsyncImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.ic_artist
import player.app.common.generated.resources.ic_options
import player.app.common.generated.resources.tracks_count
import ua.kucher.player.theme.PlayerTheme

@Composable
internal fun ArtistItem(
    modifier: Modifier = Modifier,
    name: String,
    artwork: String,
    numberOfSongs: Int,
    onClick: () -> Unit,
    onMenuClick: () -> Unit
) {
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
                .size(PlayerTheme.dimens.songIconSize)
                .clip(CircleShape)
                .background(PlayerTheme.colorScheme.rippleColor),
            model = artwork,
            contentDescription = name,
            contentScale = ContentScale.Crop,
            error = {
                Image(
                    painter = painterResource(Res.drawable.ic_artist),
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
                text = name,
                color = PlayerTheme.colorScheme.primaryTextColor,
                fontStyle = PlayerTheme.typography.largeBody.fontStyle,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens2Px))

            Text(
                text = stringResource(Res.string.tracks_count, numberOfSongs),
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
internal fun ArtistItemPreview() {
    ArtistItem(
        modifier = Modifier.background(PlayerTheme.colorScheme.primaryBackground),
        name = "SAMURAI",
        artwork = "",
        numberOfSongs = 4,
        onClick = {},
        onMenuClick = {}
    )
}
