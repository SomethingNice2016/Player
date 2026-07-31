package ua.kucher.player.playback

import android.os.Bundle
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaController
import androidx.media3.session.SessionCommands
import ua.kucher.player.entity.PlaylistItem


private const val ARTIST_ID_KEY = "ARTIST_ID_KEY"
private const val ALBUM_ID_KEY = "ALBUM_ID_KEY"
private const val DEFAULT_ID = -1L

internal fun PlaylistItem.toMediaItem(): MediaItem {

    val artworkUri = artwork?.toUri()

    val extras = Bundle().apply {
        artistId?.let { nonNullId ->
            putLong(ARTIST_ID_KEY, nonNullId)
        }
        albumId?.let { nonNullId ->
            putLong(ALBUM_ID_KEY, nonNullId)
        }
    }

    val metadata = MediaMetadata.Builder()
        .setTitle(title)
        .setAlbumTitle(albumTitle)
        .setArtist(artistTitle)
        .setIsPlayable(true)
        .setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
        .setExtras(extras)
        .setArtworkUri(artworkUri)
        .build()

    return MediaItem.Builder()
        .setMediaId(id.toString())
        .setMediaMetadata(metadata)
        .setUri(uri)
        .build()
}

internal val MediaItem.artistId: Long?
    get() = mediaMetadata.extras?.getLong(ARTIST_ID_KEY, DEFAULT_ID).takeIf { id ->
        id != DEFAULT_ID
    }

internal val MediaItem.albumId: Long?
    get() = mediaMetadata.extras?.getLong(ALBUM_ID_KEY, DEFAULT_ID).takeIf { id ->
        id != DEFAULT_ID
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
