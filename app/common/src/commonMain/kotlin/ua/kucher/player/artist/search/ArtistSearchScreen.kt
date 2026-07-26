package ua.kucher.player.artist.search

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
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.ic_arrow_left
import player.app.common.generated.resources.ic_search
import player.app.common.generated.resources.search_artist_hint
import ua.kucher.player.common.ArtistUi
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.ArtistsList
import ua.kucher.player.theme.components.PlayerTextField
import ua.kucher.player.theme.components.items.PlayerMenuIconButton

@Composable
internal fun ArtistSearchScreen(
    uiState: ArtistSearchUiState,
    onSearch: (String) -> Unit,
    onArtistClick: (id: Long) -> Unit,
    onMenuClick: (id: Long) -> Unit,
    onBackClick: () -> Unit
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
                    imageVector = vectorResource(Res.drawable.ic_arrow_left),
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
                    hint = stringResource(Res.string.search_artist_hint),
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
                            imageVector = vectorResource(Res.drawable.ic_search),
                            iconSize = PlayerTheme.dimens.dimens16Px,
                            contentDescription = null,
                        )
                    }
                )
            }
        }
    ) { paddings ->
        ArtistsList(
            modifier = Modifier
                .fillMaxSize()
                .background(PlayerTheme.colorScheme.primaryBackground)
                .padding(paddings),
            artists = uiState.searchResult,
            lazyListState = lazyListState,
            onArtistClick = { id ->
                keyboardController?.hide()
                onArtistClick(id)
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
    val artistUi = ArtistUi(
        id = 12,
        name = "SAMURAI",
        artwork = "",
        numberOfSongs = 12,
        numberOfAlbums = 3
    )
    ArtistSearchScreen(
        uiState = ArtistSearchUiState(
            searchQuery = "Never",
            searchResult = listOf(
                artistUi,
                artistUi.copy(id = 13),
                artistUi.copy(id = 14),
                artistUi.copy(id = 15),
            )
        ),
        onSearch = {},
        onArtistClick = {},
        onMenuClick = {},
        onBackClick = {}
    )
}