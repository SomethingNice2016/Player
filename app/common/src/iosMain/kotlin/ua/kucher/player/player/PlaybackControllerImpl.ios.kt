package ua.kucher.player.player

import kotlinx.coroutines.flow.Flow
import ua.kucher.player.entity.Playlist
import ua.kucher.player.entity.PlaylistItem
import ua.kucher.player.entity.Song

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
internal class PlaybackControllerImpl : PlaybackController {

    override val isPlaying: Flow<Boolean>
        get() = TODO("Not yet implemented")
    override val currentItem: Flow<PlaylistItem?>
        get() = TODO("Not yet implemented")
    override val currentPlaylist: Flow<Playlist?>
        get() = TODO("Not yet implemented")

    override fun prepare(playlist: Playlist) {
        TODO("Not yet implemented")
    }

    override fun play(item: PlaylistItem) {
        TODO("Not yet implemented")
    }

    override val currentSongId: Flow<Long>
        get() = TODO("Not yet implemented")

    override fun prepare(songs: List<Song>) {

    }

    override fun play(song: Song) {
        TODO("Not yet implemented")
    }

    override fun playPause() {
        TODO("Not yet implemented")
    }

    override fun forward() {
        TODO("Not yet implemented")
    }

    override fun back() {
        TODO("Not yet implemented")
    }
}