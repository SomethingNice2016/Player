package ua.kucher.player.navigation

import kotlinx.serialization.Serializable
import ua.kucher.player.core.common.uuid.uuid


@Serializable
internal sealed class AppRoute : Route {

    @Serializable
    data class Home(
        override val id: String = uuid()
    ) : AppRoute()

    @Serializable
    data class AllSong(
        override val id: String = uuid()
    ) : AppRoute()

    @Serializable
    data class Settings(
        override val id: String = uuid()
    ) : AppRoute()

    @Serializable
    data class SongsSearch(
        override val id: String = uuid()
    ) : AppRoute()

    @Serializable
    data class ArtistList(
        override val id: String = uuid()
    ) : AppRoute()

    @Serializable
    data class ArtistSearch(
        override val id: String = uuid()
    ) : AppRoute()

    @Serializable
    data class AlbumsList(
        override val id: String = uuid()
    ) : AppRoute()

    @Serializable
    data class AlbumSearch(
        override val id: String = uuid()
    ) : AppRoute()

    @Serializable
    data class FavoriteSongs(
        override val id: String = uuid()
    ) : AppRoute()

    @Serializable
    data class Album(
        val albumId: Long,
        override val id: String = uuid(),
    ) : AppRoute()

    @Serializable
    data class Artist(
        val artistId: Long,
        override val id: String = uuid(),
    ) : AppRoute()
}