package ua.kucher.player.theme.components.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import coil3.compose.SubcomposeAsyncImage
import org.jetbrains.compose.resources.painterResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.ic_artist
import ua.kucher.player.theme.PlayerTheme


@Composable
internal fun ArtistGridItem(
    modifier: Modifier = Modifier,
    name: String,
    artwork: String?,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .width(PlayerTheme.dimens.dimens80Px)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(PlayerTheme.dimens.dimens80Px)
                .clip(CircleShape)
                .background(PlayerTheme.colorScheme.menuEnableButton),
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
                        .padding(PlayerTheme.dimens.dimens16Px)
                )
            }
        )

        Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens8Px))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = name,
            textAlign = TextAlign.Center,
            color = PlayerTheme.colorScheme.primaryTextColor,
            style = PlayerTheme.typography.smallBody,
            maxLines = 1
        )
    }
}