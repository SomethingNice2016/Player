package ua.kucher.player.local.song.entity

internal data class MergeSongsResult(
    val removedSongIds: Set<Long>,
    val insertedSongs: List<SongEntity>,
)
