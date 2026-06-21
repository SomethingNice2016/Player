package ua.kucher.player.playback

import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaController
import androidx.media3.session.SessionCommands
import ua.kucher.player.entity.Playlist
import ua.kucher.player.entity.PlaylistItem


private const val PLAYLIST_ID_KEY = "PLAYLIST_ID"

internal fun Playlist.toMediaItems() = items.map { item ->
    item.toMediaItem(id)
}

internal fun PlaylistItem.toMediaItem(playlistId: Long = -1): MediaItem {

    val artworkUri = artwork?.toUri()

    val extras = bundleOf(PLAYLIST_ID_KEY to playlistId)

    val metadata = MediaMetadata.Builder()
        .setAlbumTitle(albumTitle)
        .setTitle(title)
        .setIsPlayable(true)
        .setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
        .setArtist(artistTitle)
        .setArtworkUri(artworkUri)
        .setExtras(extras)
        .build()

    return MediaItem.Builder()
        .setMediaId(id.toString())
        .setMediaMetadata(metadata)
        .setUri(uri)
        .build()
}

internal fun SessionCommands.Builder.addAll(list: List<CommandButton>): SessionCommands.Builder {
    list.mapNotNull { it.sessionCommand }.forEach {
        add(it)
    }
    return this
}

internal val MediaController.mediaItems: List<MediaItem>
    get() = (0 until mediaItemCount).mapNotNull { index ->
        getMediaItemAt(index)
    }

internal val MediaController.nextMediaItemId: Long?
    get() {
        val nextIndex = currentMediaItemIndex + 1
        if (nextIndex >= mediaItemCount) {
            return null
        }
        return getMediaItemAt(nextIndex)
            .mediaId
            .toLongOrNull()
    }

internal val MediaController.previousMediaItemId: Long?
    get() {
        val previousIndex = currentMediaItemIndex - 1
        if (previousIndex < 0) {
            return null
        }
        return getMediaItemAt(previousIndex)
            .mediaId
            .toLongOrNull()
    }

internal fun List<MediaItem>.asPlaylistItems(): List<PlaylistItem> =
    mapNotNull { item ->
        item.localConfiguration?.tag as? PlaylistItem
    }

internal val MediaItem.playlistId: Long
    get() = mediaMetadata.extras?.getLong(PLAYLIST_ID_KEY) ?: -1

internal fun PlaybackController.RepeatMode.Companion.fromPlayerRepeatMode(mode: Int) =
    when (mode) {
        Player.REPEAT_MODE_OFF -> PlaybackController.RepeatMode.OFF
        Player.REPEAT_MODE_ONE -> PlaybackController.RepeatMode.ONE
        Player.REPEAT_MODE_ALL -> PlaybackController.RepeatMode.ALL
        else -> PlaybackController.RepeatMode.OFF
    }

fun PlaybackController.RepeatMode.toPlayerRepeatMode(): Int {
    return when (this) {
        PlaybackController.RepeatMode.OFF -> Player.REPEAT_MODE_OFF
        PlaybackController.RepeatMode.ALL -> Player.REPEAT_MODE_ALL
        PlaybackController.RepeatMode.ONE -> Player.REPEAT_MODE_ONE
    }
}
