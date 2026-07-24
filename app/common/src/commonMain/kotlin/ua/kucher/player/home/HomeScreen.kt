package ua.kucher.player.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalGridApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.albums
import player.app.common.generated.resources.albums_count
import player.app.common.generated.resources.artists
import player.app.common.generated.resources.artists_count
import player.app.common.generated.resources.default_song_artwork
import player.app.common.generated.resources.favorites
import player.app.common.generated.resources.home_label
import player.app.common.generated.resources.home_screen_items_count
import player.app.common.generated.resources.ic_album
import player.app.common.generated.resources.ic_arrow_right
import player.app.common.generated.resources.ic_artist
import player.app.common.generated.resources.ic_favorite
import player.app.common.generated.resources.ic_playlist
import player.app.common.generated.resources.playlists
import player.app.common.generated.resources.playlists_count
import player.app.common.generated.resources.recently_played
import player.app.common.generated.resources.see_all
import player.app.common.generated.resources.top_artists
import player.app.common.generated.resources.tracks_count
import ua.kucher.player.common.ArtistUi
import ua.kucher.player.common.SongUi
import ua.kucher.player.core.ui.utils.canScroll
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.PlayerTopAppBar
import ua.kucher.player.theme.components.PlayerTopAppBarDefaults
import ua.kucher.player.theme.components.items.ArtistGridItem
import ua.kucher.player.theme.components.items.SongGridItem
import ua.kucher.player.theme.components.rememberPlayerTopAppBarState
import ua.kucher.player.theme.extensions.BottomNavSpacer

@OptIn(ExperimentalGridApi::class)
@Composable
internal fun HomeScreen(
    uiState: HomeScreenUiState,
    onRefresh: () -> Unit,
    onSeeAllArtists: () -> Unit,
    onSeeAllSongs: () -> Unit,
    onSeeAllAlbums: () -> Unit,
    onFavoriteSongsClick: () -> Unit,
    onSongClick: (id: Long) -> Unit,
    onArtistClick: (id: Long) -> Unit,
    showSongMenu: (id: Long) -> Unit
) {

    val pullToRefreshState = rememberPullToRefreshState()

    val scrollableState = rememberScrollState()

    val scrollBehavior = PlayerTopAppBarDefaults.scrollBehavior()

    val topAppBarState = rememberPlayerTopAppBarState(scrollBehavior)

    val modifier = Modifier
        .fillMaxSize()
        .then(
            scrollableState.canScroll
                .takeIf { it }
                ?.let { Modifier.nestedScroll(scrollBehavior.nestedScrollConnection) }
                ?: Modifier
        )

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0),
        containerColor = Color.Transparent,
        topBar = {
            PlayerTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                titleRes = Res.string.home_label,
                scrollBehavior = scrollBehavior,
                navigationIcon = {},
                showDivider = { false },
            )
        }
    ) { paddingValues ->
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = pullToRefreshState,
            isRefreshing = uiState.isRefreshing,
            enabled = topAppBarState.isExpanded,
            onRefresh = onRefresh,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollableState)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = PlayerTheme.dimens.dimens16Px),
                    text = stringResource(
                        Res.string.home_screen_items_count,
                        uiState.songsCount,
                        uiState.artistsCount,
                        uiState.albumCount
                    ),
                    style = PlayerTheme.typography.mediumBody,
                    color = PlayerTheme.colorScheme.secondaryTextColor
                )

                Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens16Px))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = PlayerTheme.dimens.dimens16Px),
                    verticalArrangement = Arrangement.spacedBy(PlayerTheme.dimens.dimens8Px)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(PlayerTheme.dimens.dimens8Px),
                    ) {
                        HomeScreenTab(
                            modifier = Modifier.weight(1F),
                            primaryText = stringResource(Res.string.favorites),
                            secondaryText = stringResource(Res.string.tracks_count, uiState.favoriteSongsCount),
                            painter = painterResource(Res.drawable.ic_favorite),
                            onClick = onFavoriteSongsClick
                        )

                        HomeScreenTab(
                            modifier = Modifier.weight(1F),
                            primaryText = stringResource(Res.string.playlists),
                            secondaryText = stringResource(Res.string.playlists_count, uiState.favoriteSongsCount),
                            painter = painterResource(Res.drawable.ic_playlist),
                            onClick = {}
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(PlayerTheme.dimens.dimens8Px),
                    ) {
                        HomeScreenTab(
                            modifier = Modifier.weight(1F),
                            primaryText = stringResource(Res.string.albums),
                            secondaryText = stringResource(Res.string.albums_count, uiState.albumCount),
                            painter = painterResource(Res.drawable.ic_album),
                            onClick = onSeeAllAlbums
                        )

                        HomeScreenTab(
                            modifier = Modifier.weight(1F),
                            primaryText = stringResource(Res.string.artists),
                            secondaryText = stringResource(Res.string.artists_count, uiState.artistsCount),
                            painter = painterResource(Res.drawable.ic_artist),
                            onClick = onSeeAllArtists
                        )
                    }
                }

                Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens16Px))

                if (uiState.recentlyPlayedSongs.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = PlayerTheme.dimens.dimens16Px),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.recently_played),
                            style = PlayerTheme.typography.mediumTitle,
                            color = PlayerTheme.colorScheme.primaryTextColor
                        )
                        Spacer(modifier = Modifier.weight(1F))
                        Text(
                            text = stringResource(Res.string.see_all),
                            style = PlayerTheme.typography.smallTitle,
                            color = PlayerTheme.colorScheme.menuEnableButton,
                            modifier = Modifier.clickable {
                                onSeeAllSongs()
                            },
                        )
                    }

                    val songPlaceholder = painterResource(Res.drawable.default_song_artwork)

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(
                            horizontal = PlayerTheme.dimens.dimens8Px,
                        ),
                    ) {
                        items(
                            items = uiState.recentlyPlayedSongs,
                            key = { song -> song.id }
                        ) { song ->
                            SongGridItem(
                                title = song.title,
                                artist = song.artistName,
                                artwork = song.artwork,
                                placeholder = songPlaceholder,
                                isSongPlaying = song.id == uiState.playingSongId,
                                isPlaying = uiState.isPlaying,
                                onClick = {
                                    onSongClick(song.id)
                                },
                                onLongClick = {
                                    showSongMenu(song.id)
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens8Px))
                }

                if (uiState.topArtists.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = PlayerTheme.dimens.dimens16Px),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.top_artists),
                            style = PlayerTheme.typography.mediumTitle,
                            color = PlayerTheme.colorScheme.primaryTextColor
                        )
                        Spacer(modifier = Modifier.weight(1F))
                        Text(
                            text = stringResource(Res.string.see_all),
                            style = PlayerTheme.typography.smallTitle,
                            color = PlayerTheme.colorScheme.menuEnableButton,
                            modifier = Modifier.clickable {
                                onSeeAllArtists()
                            },
                        )
                    }
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(
                            horizontal = PlayerTheme.dimens.dimens8Px,
                        ),
                    ) {
                        items(
                            items = uiState.topArtists,
                            key = { artist -> artist.id }
                        ) { artist ->
                            ArtistGridItem(
                                name = artist.name,
                                artwork = artist.artwork,
                                onClick = {
                                    onArtistClick(artist.id)
                                }
                            )
                        }
                    }
                }
                BottomNavSpacer()
            }
        }
    }
}

@Composable
private fun HomeScreenTab(
    modifier: Modifier = Modifier,
    primaryText: String,
    secondaryText: String,
    painter: Painter,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(PlayerTheme.shapes.radius16Px)
            .background(PlayerTheme.colorScheme.rippleColor)
            .clickable(onClick = onClick)
            .padding(
                vertical = PlayerTheme.dimens.dimens16Px,
                horizontal = PlayerTheme.dimens.dimens12Px
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .clip(PlayerTheme.shapes.radius12Px)
                .background(PlayerTheme.colorScheme.menuEnableButton)
                .padding(PlayerTheme.dimens.dimens8Px)
        ) {
            Image(
                modifier = Modifier.size(PlayerTheme.dimens.settingItemSize),
                painter = painter,
                contentDescription = primaryText
            )
        }

        Spacer(modifier = Modifier.width(PlayerTheme.dimens.dimens12Px))

        Column(modifier = Modifier.weight(1F)) {
            Text(
                text = primaryText,
                style = PlayerTheme.typography.mediumBody,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = PlayerTheme.colorScheme.primaryTextColor
            )
            Spacer(modifier = Modifier.height(PlayerTheme.dimens.dimens2Px))
            Text(
                text = secondaryText,
                style = PlayerTheme.typography.smallBody,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = PlayerTheme.colorScheme.secondaryTextColor
            )
        }
        Image(
            painter = painterResource(Res.drawable.ic_arrow_right),
            contentDescription = primaryText,
            colorFilter = ColorFilter.tint(PlayerTheme.colorScheme.secondaryTextColor)
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {

    val songUi = SongUi(
        id = 1L,
        title = "Never fade away",
        artistName = "Samurai",
        displayDuration = "2:22",
        duration = 100000L,
        artwork = "",
        isFavorite = false
    )

    val artistUi = ArtistUi(
        id = 1L,
        name = "Samurai",
        artwork = "",
        numberOfSongs = 22,
        numberOfAlbums = 3
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PlayerTheme.colorScheme.primaryBackground)
    ) {
        HomeScreen(
            uiState = HomeScreenUiState(
                isRefreshing = false,
                songsCount = 123,
                artistsCount = 33,
                albumCount = 3,
                recentlyPlayedSongs = listOf(
                    songUi,
                    songUi.copy(id = 2L),
                    songUi.copy(id = 3L),
                    songUi.copy(id = 4L)
                ),
                topArtists = listOf(
                    artistUi,
                    artistUi.copy(id = 2L),
                    artistUi.copy(id = 3L),
                    artistUi.copy(id = 4L)
                )
            ),
            onRefresh = {},
            onSeeAllAlbums = {},
            onSeeAllArtists = {},
            onSeeAllSongs = {},
            onSongClick = {},
            onArtistClick = {},
            onFavoriteSongsClick = {},
            showSongMenu = {}
        )
    }
}