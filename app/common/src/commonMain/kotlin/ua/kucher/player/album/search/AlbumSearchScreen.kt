package ua.kucher.player.album.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.ic_arrow_left
import player.app.common.generated.resources.ic_search
import player.app.common.generated.resources.search_album_hint
import ua.kucher.player.common.AlbumUi
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.AlbumsList
import ua.kucher.player.theme.components.PlayerTextField
import ua.kucher.player.theme.components.items.PlayerMenuIconButton

@Composable
internal fun AlbumSearchScreen(
    uiState: AlbumSearchUiState,
    onBackClick: () -> Unit,
    onSearch: (String) -> Unit,
    onAlbumClick: (id: Long) -> Unit,
    onMenuClick: (id: Long) -> Unit,
) {
    val focusRequester = remember {
        FocusRequester()
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(PlayerTheme.colorScheme.primaryBackground),
        contentWindowInsets = WindowInsets(0),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(vertical = PlayerTheme.dimens.dimens8Px)
                    .padding(end = PlayerTheme.dimens.dimens16Px)
            ) {

                PlayerMenuIconButton(
                    painter = painterResource(Res.drawable.ic_arrow_left),
                    onClick = {
                        keyboardController?.hide()
                        onBackClick()
                    }
                )

                PlayerTextField(
                    modifier = Modifier
                        .weight(1F)
                        .focusRequester(focusRequester),
                    textValue = uiState.searchQuery,
                    hint = stringResource(Res.string.search_album_hint),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            keyboardController?.hide()
                        }
                    ),
                    onNewText = onSearch,
                    leadingIcon = {
                        PlayerMenuIconButton(
                            painter = painterResource(Res.drawable.ic_search),
                            iconSize = PlayerTheme.dimens.dimens16Px,
                            contentDescription = null,
                        )
                    }
                )
            }
        }
    ) { paddings ->
        AlbumsList(
            modifier = Modifier
                .fillMaxSize()
                .background(PlayerTheme.colorScheme.primaryBackground)
                .padding(paddings),
            albums = uiState.searchResult,
            lazyListState = lazyListState,
            onArtistClick = { id ->
                keyboardController?.hide()
                onAlbumClick(id)
            },
            onMenuClick = { id ->
                keyboardController?.hide()
                onMenuClick(id)
            }
        )
    }
}

@Preview
@Composable
private fun ArtistSearchScreenPreview() {
    val album = AlbumUi(
        id = 1L,
        title = "Never fade away",
        artistName = "SAMURAI",
        numberOfSongs = 6,
        artwork = ""
    )

    AlbumSearchScreen(
        uiState = AlbumSearchUiState(
            searchQuery = "Never",
            searchResult = listOf(
                album,
                album.copy(id = 13),
                album.copy(id = 14),
                album.copy(id = 15),
            )
        ),
        onSearch = {},
        onAlbumClick = {},
        onMenuClick = {},
        onBackClick = {}
    )
}