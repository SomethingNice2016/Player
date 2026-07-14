package ua.kucher.player.song.list

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
import player.app.common.generated.resources.ic_search
import player.app.common.generated.resources.music_label
import player.app.common.generated.resources.search
import ua.kucher.player.common.SongUi
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.PlayerTopAppBar
import ua.kucher.player.theme.components.PlayerTopAppBarDefaults
import ua.kucher.player.theme.components.SongsList
import ua.kucher.player.theme.components.items.PlayerMenuIconButton
import ua.kucher.player.theme.components.rememberPlayerTopAppBarState


@Composable
internal fun SongListScreen(
    uiState: SongListUiState,
    onSongClick: (id: Long) -> Unit,
    onMenuClick: (id: Long) -> Unit,
    onRefresh: () -> Unit,
    onSearch: () -> Unit
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
                titleRes = Res.string.music_label,
                navigationIcon = {},
                showDivider = { false },
                scrollBehavior = scrollBehavior,
                actions = {
                    PlayerMenuIconButton(
                        painter = painterResource(Res.drawable.ic_search),
                        contentDescription = stringResource(Res.string.search),
                        onClick = onSearch
                    )
                }
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
            SongsList(
                songs = uiState.songs,
                isPlaying = uiState.isPlaying,
                playingSongId = uiState.playingSongId,
                lazyListState = lazyListState,
                onSongClick = onSongClick,
                onMenuClick = onMenuClick
            )
        }
    }
}

@Preview
@Composable
private fun SongListScreenPreview() {

    val songUi = SongUi(
        id = 12,
        title = "Never fade away",
        artistName = "SAMURAI",
        displayDuration = "3:33",
        duration = 69000L,
        artwork = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PlayerTheme.colorScheme.primaryBackground)
    ) {
        SongListScreen(
            uiState = SongListUiState(
                songs = listOf(
                    songUi,
                    songUi.copy(id = 13),
                    songUi.copy(id = 14),
                    songUi.copy(id = 15),
                ),
                isPlaying = true,
                playingSongId = 12L
            ),
            onSongClick = {},
            onRefresh = {},
            onSearch = {},
            onMenuClick = {}
        )
    }
}