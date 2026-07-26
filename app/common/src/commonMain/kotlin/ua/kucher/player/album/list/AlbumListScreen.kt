package ua.kucher.player.album.list

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
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.albums
import player.app.common.generated.resources.ic_arrow_left
import player.app.common.generated.resources.ic_search
import player.app.common.generated.resources.search
import ua.kucher.player.common.AlbumUi
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.AlbumsList
import ua.kucher.player.theme.components.PlayerTopAppBar
import ua.kucher.player.theme.components.PlayerTopAppBarDefaults
import ua.kucher.player.theme.components.items.PlayerMenuIconButton
import ua.kucher.player.theme.components.rememberPlayerTopAppBarState

@Composable
internal fun AlbumListScreen(
    uiState: AlbumListUiState,
    onBackClick: () -> Unit,
    onSearch: () -> Unit,
    onRefresh: () -> Unit,
    onAlbumClick: (id: Long) -> Unit,
    onMenuClick: (id: Long) -> Unit
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
                titleRes = Res.string.albums,
                navigationIcon = {
                    PlayerMenuIconButton(
                        imageVector = vectorResource(Res.drawable.ic_arrow_left),
                        onClick = onBackClick
                    )
                },
                actions = {
                    PlayerMenuIconButton(
                        imageVector = vectorResource(Res.drawable.ic_search),
                        contentDescription = stringResource(Res.string.search),
                        iconSize = PlayerTheme.dimens.dimens20Px,
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
            AlbumsList(
                modifier = Modifier.fillMaxSize(),
                lazyListState = lazyListState,
                albums = uiState.albums,
                onArtistClick = onAlbumClick,
                onMenuClick = onMenuClick
            )
        }
    }
}


@Preview
@Composable
private fun AlbumListScreenPreview() {

    val album = AlbumUi(
        id = 1L,
        title = "Never fade away",
        artistName = "SAMURAI",
        numberOfSongs = 6,
        artwork = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PlayerTheme.colorScheme.primaryBackground)
    ) {
        AlbumListScreen(
            uiState = AlbumListUiState(
                isRefreshing = false,
                isPlayerShowed = false,
                albums = listOf(
                    album,
                    album.copy(id = 2L),
                    album.copy(id = 3L),
                    album.copy(id = 4L)
                )
            ),
            onBackClick = {},
            onSearch = {},
            onRefresh = {},
            onAlbumClick = {},
            onMenuClick = {}
        )
    }
}