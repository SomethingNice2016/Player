package ua.kucher.player.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.ic_cast
import player.app.common.generated.resources.ic_pause_outline
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.items.PlayerMenuIconButton

@Composable
internal fun MiniPlayer(
    modifier: Modifier = Modifier,
    title: String,
    artist: String
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(
                horizontal = PlayerTheme.dimens.dimens16Px,
                vertical = PlayerTheme.dimens.dimens8Px
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(modifier = Modifier.size(48.dp))

        Spacer(Modifier.width(PlayerTheme.dimens.dimens16Px))

        Column(modifier = Modifier.weight(1F)) {
            Text(
                text = title,
                color = PlayerTheme.colorScheme.primaryTextColor,
                fontStyle = PlayerTheme.typography.largeBody.fontStyle,
                maxLines = 2,
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

        PlayerMenuIconButton(
            painter = painterResource(Res.drawable.ic_cast),
            contentDescription = "",
            onClick = {}
        )

        PlayerMenuIconButton(
            painter = painterResource(Res.drawable.ic_pause_outline),
            contentDescription = "",
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun MiniPlayerPreview() {
    MiniPlayer(
        title = "",
        artist = ""
    )
}