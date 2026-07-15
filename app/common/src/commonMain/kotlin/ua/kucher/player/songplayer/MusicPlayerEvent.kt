package ua.kucher.player.songplayer

internal sealed interface MusicPlayerEvent {
    data object CollapsePlayer : MusicPlayerEvent
    data object ExpandPlayer : MusicPlayerEvent
}