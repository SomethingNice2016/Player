package ua.kucher.player.player

import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import ua.kucher.player.entity.PlaylistItem


internal fun PlaylistItem.toMediaItem(): MediaItem {

    val artworkUri = artwork?.toUri()

    val metadata = MediaMetadata.Builder()
        .setAlbumTitle(albumTitle)
        .setTitle(title)
        .setIsPlayable(true)
        .setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
        .setArtist(artistTitle)
        .setArtworkUri(artworkUri)
        .build()

    return MediaItem.Builder()
        .setMediaId(id.toString())
        .setMediaMetadata(metadata)
        .setUri(uri)
        .build()
}
