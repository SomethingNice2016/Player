package ua.kucher.player.songlist

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.ic_search
import player.app.common.generated.resources.music_label
import player.app.common.generated.resources.search
import ua.kucher.player.theme.components.PlayerTopAppBar
import ua.kucher.player.theme.components.PlayerTopAppBarDefaults
import ua.kucher.player.theme.components.items.PlayerMenuIconButton
import ua.kucher.player.theme.components.items.SongItem
import ua.kucher.player.theme.extensions.BottomNavSpacer
import ua.kucher.player.theme.extensions.MiniPlayerSpacer


@Composable
internal fun SongListScreen(
    uiState: SongListUiState,
    onSongClick: (songId: Long) -> Unit
) {

    val lazyListState = rememberLazyListState()

    val scrollBehavior = PlayerTopAppBarDefaults.scrollBehavior()

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
                        onClick = {}
                    )
                }
            )
        }
    ) { paddingValues ->
        SongsListContent(
            modifier = Modifier.padding(paddingValues),
            uiState = uiState,
            lazyListState = lazyListState,
            onSongClick = onSongClick
        )
    }
}

@Composable
private fun SongsListContent(
    modifier: Modifier = Modifier,
    uiState: SongListUiState,
    lazyListState: LazyListState,
    onSongClick: (songId: Long) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
    ) {
        items(
            items = uiState.songs,
            key = { song -> song.id },
            contentType = { "song" }
        ) { song ->
            SongItem(
                modifier = Modifier.fillMaxWidth(),
                title = song.title,
                artist = song.artistName,
                artwork = song.artwork,
                duration = song.displayDuration,
                isSongPlaying = song.id == uiState.playingSongId,
                isPlaying = uiState.isPlaying,
                onClick = { onSongClick(song.id) }
            )
        }
        if (uiState.isPlayerShowed) {
            item { MiniPlayerSpacer() }
        }
        item { BottomNavSpacer() }
    }
}