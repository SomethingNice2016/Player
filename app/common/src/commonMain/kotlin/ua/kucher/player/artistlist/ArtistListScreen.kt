package ua.kucher.player.artistlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.artists
import player.app.common.generated.resources.ic_arrow_left
import player.app.common.generated.resources.ic_search
import player.app.common.generated.resources.search
import ua.kucher.player.common.ArtistUi
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.ArtistsList
import ua.kucher.player.theme.components.PlayerTopAppBar
import ua.kucher.player.theme.components.PlayerTopAppBarDefaults
import ua.kucher.player.theme.components.items.PlayerMenuIconButton
import ua.kucher.player.theme.components.rememberPlayerTopAppBarState

@Composable
internal fun ArtistListScreen(
    uiState: ArtistListUiState,
    onArtistClick: (id: Long) -> Unit,
    onMenuClick: (id: Long) -> Unit,
    onRefresh: () -> Unit,
    onSearch: () -> Unit,
    onBackClick: () -> Unit
) {
    val lazyListState = rememberLazyListState()

    val scrollBehavior = PlayerTopAppBarDefaults.scrollBehavior()

    val topAppBarState = rememberPlayerTopAppBarState(scrollBehavior)

    val pullToRefreshState = rememberPullToRefreshState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = WindowInsets(0),
        containerColor = Color.Transparent,
        topBar = {
            PlayerTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                titleRes = Res.string.artists,
                navigationIcon = {
                    PlayerMenuIconButton(
                        painter = painterResource(Res.drawable.ic_arrow_left),
                        onClick = onBackClick
                    )
                },
                actions = {
                    PlayerMenuIconButton(
                        painter = painterResource(Res.drawable.ic_search),
                        contentDescription = stringResource(Res.string.search),
                        onClick = onSearch
                    )
                },
                showDivider = { false },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { paddingValues ->
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = pullToRefreshState,
            isRefreshing = uiState.isRefreshing,
            onRefresh = onRefresh,
            enabled = topAppBarState.isExpanded
        ) {
            ArtistsList(
                modifier = Modifier.fillMaxSize(),
                lazyListState = lazyListState,
                artists = uiState.artists,
                isPlayerShowed = uiState.isPlayerShowed,
                onArtistClick = onArtistClick,
                onMenuClick = onMenuClick
            )
        }
    }
}

@Preview
@Composable
private fun ArtistListScreenPreview() {

    val artistUi = ArtistUi(
        id = 1L,
        name = "Samurai",
        artwork = "",
        numberOfSongs = 22
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PlayerTheme.colorScheme.primaryBackground)
    )
    ArtistListScreen(
        uiState = ArtistListUiState(
            artists = listOf(
                artistUi,
                artistUi.copy(id = 2L),
                artistUi.copy(id = 3L),
                artistUi.copy(id = 4L),
                artistUi.copy(id = 5L),
            )
        ),
        onArtistClick = {},
        onRefresh = {},
        onSearch = {},
        onBackClick = {},
        onMenuClick = {}
    )
}