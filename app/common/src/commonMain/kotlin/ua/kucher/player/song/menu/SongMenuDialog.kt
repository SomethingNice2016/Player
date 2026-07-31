package ua.kucher.player.song.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.add_to_playlist
import player.app.common.generated.resources.ic_add_to_playlist
import player.app.common.generated.resources.ic_album
import player.app.common.generated.resources.ic_artist
import player.app.common.generated.resources.ic_cross
import player.app.common.generated.resources.ic_like
import player.app.common.generated.resources.ic_like_outline
import player.app.common.generated.resources.ic_play_next
import player.app.common.generated.resources.ic_share
import player.app.common.generated.resources.play_next
import player.app.common.generated.resources.share
import player.app.common.generated.resources.song_item_description
import player.app.common.generated.resources.song_menu_item_add_to_queue
import player.app.common.generated.resources.song_menu_item_go_to_album
import player.app.common.generated.resources.song_menu_item_go_to_artist
import ua.kucher.player.common.SongUi
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.items.PlayerMenuIconButton

@Composable
internal fun SongMenuDialog(
    uiState: SongMenuUiState,
    showAlbumsItem: Boolean,
    showArtistItem: Boolean,
    onBackClick: () -> Unit,
    setFavoriteState: () -> Unit,
    onPlayNextClick: () -> Unit,
    onShareClick: () -> Unit,
    goToArtist: () -> Unit,
    goToAlbum: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PlayerTheme.dimens.dimens8Px)
            .clip(PlayerTheme.shapes.radius8Px)
            .background(PlayerTheme.colorScheme.primaryBackground)
            .padding(vertical = PlayerTheme.dimens.dimens16Px)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PlayerTheme.dimens.dimens16Px)
        ) {
            Column(modifier = Modifier.weight(1F)) {
                Text(
                    text = uiState.song?.title.orEmpty(),
                    color = PlayerTheme.colorScheme.primaryTextColor,
                    style = PlayerTheme.typography.mediumTitle,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                )

                Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens4Px))

                Text(
                    text = stringResource(
                        Res.string.song_item_description,
                        uiState.song?.artistName.orEmpty(),
                        uiState.song?.displayDuration.orEmpty()
                    ),
                    color = PlayerTheme.colorScheme.secondaryTextColor,
                    style = PlayerTheme.typography.smallTitle,
                    overflow = TextOverflow.MiddleEllipsis,
                    maxLines = 1,
                )
            }

            val likeIcon = if (uiState.song?.isFavorite == true)
                Res.drawable.ic_like
            else
                Res.drawable.ic_like_outline

            PlayerMenuIconButton(
                imageVector = vectorResource(likeIcon),
                onClick = setFavoriteState
            )

            PlayerMenuIconButton(
                imageVector = vectorResource(Res.drawable.ic_cross),
                onClick = onBackClick
            )
        }

        Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens12Px))

        HorizontalDivider(
            thickness = PlayerTheme.dimens.dimens1Px,
            color = PlayerTheme.colorScheme.secondaryTextColor,
        )

        Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens16Px))

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = PlayerTheme.dimens.dimens16Px),
                horizontalArrangement = Arrangement.spacedBy(PlayerTheme.dimens.dimens16Px)
            ) {

                MenuGridItem(
                    modifier = Modifier.weight(1F),
                    icon = painterResource(Res.drawable.ic_play_next),
                    text = stringResource(Res.string.play_next),
                    onClick = onPlayNextClick
                )

                MenuGridItem(
                    modifier = Modifier.weight(1F),
                    icon = painterResource(Res.drawable.ic_add_to_playlist),
                    text = stringResource(Res.string.add_to_playlist),
                    onClick = onPlayNextClick
                )

                MenuGridItem(
                    modifier = Modifier.weight(1F),
                    icon = painterResource(Res.drawable.ic_share),
                    text = stringResource(Res.string.share),
                    onClick = onShareClick
                )
            }

            Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens16Px))

            if (showAlbumsItem) {
                MenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.song_menu_item_go_to_album),
                    icon = painterResource(Res.drawable.ic_album),
                    onClick = goToAlbum
                )
            }

            if (showArtistItem) {
                MenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.song_menu_item_go_to_artist),
                    icon = painterResource(Res.drawable.ic_artist),
                    onClick = goToArtist
                )
            }

            MenuItem(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.song_menu_item_add_to_queue),
                icon = painterResource(Res.drawable.ic_add_to_playlist),
                onClick = {}
            )
        }
    }
}

@Composable
private fun MenuGridItem(
    modifier: Modifier = Modifier,
    text: String,
    icon: Painter,
    onClick: () -> Unit
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(PlayerTheme.shapes.radius8Px)
                .background(PlayerTheme.colorScheme.rippleColor)
                .clickable(onClick = onClick)
                .padding(PlayerTheme.dimens.dimens16Px),
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(PlayerTheme.dimens.dimens32Px),
                colorFilter = ColorFilter.tint(PlayerTheme.colorScheme.iconsMain),
                painter = icon,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens6Px))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            textAlign = TextAlign.Center,
            maxLines = 2,
            style = PlayerTheme.typography.smallBody,
            color = PlayerTheme.colorScheme.primaryTextColor
        )
    }
}

@Composable
private fun MenuItem(
    modifier: Modifier = Modifier,
    text: String,
    icon: Painter,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(
                horizontal = PlayerTheme.dimens.dimens16Px,
                vertical = PlayerTheme.dimens.dimens8Px
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(PlayerTheme.dimens.dimens32Px),
            painter = icon,
            colorFilter = ColorFilter.tint(PlayerTheme.colorScheme.iconsMain),
            contentDescription = text
        )
        Spacer(modifier = Modifier.width(PlayerTheme.dimens.dimens16Px))

        Text(
            text = text,
            color = PlayerTheme.colorScheme.primaryTextColor,
            style = PlayerTheme.typography.largeBody,
        )
    }
}

@Preview
@Composable
private fun SongMenuDialogPreview() {
    SongMenuDialog(
        uiState = SongMenuUiState(
            song = SongUi(
                id = 1L,
                title = "Never fade away",
                artistName = "Samurai",
                displayDuration = "2:22",
                duration = 121212,
                isFavorite = false,
                artwork = ""
            )
        ),
        showAlbumsItem = true,
        showArtistItem = true,
        onBackClick = {},
        onPlayNextClick = {},
        setFavoriteState = {},
        onShareClick = {},
        goToArtist = {},
        goToAlbum = {}
    )
}