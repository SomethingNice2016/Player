package ua.kucher.player.navigation


internal typealias AppRouter = Router<AppRoute>


internal fun AppRouter.navigateToAllSongs() {
    reset(AppRoute.AllSong())
}

internal fun AppRouter.navigateToSongSearch() {
    navigate(AppRoute.SongsSearch())
}

internal fun AppRouter.navigateToFavoriteSongs() {
    navigate(AppRoute.FavoriteSongs())
}

internal fun AppRouter.navigateToArtistList() {
    navigate(AppRoute.ArtistList())
}

internal fun AppRouter.navigateToArtistSearch() {
    navigate(AppRoute.ArtistSearch())
}


internal fun AppRouter.navigateToAlbumList() {
    navigate(AppRoute.AlbumsList())
}

internal fun AppRouter.navigateToAlbumSearch() {
    navigate(AppRoute.AlbumSearch())
}

internal fun AppRouter.navigateToAlbum(albumId: Long) {
    navigate(AppRoute.Album(albumId))
}

internal fun AppRouter.navigateToArtist(artistId: Long) {
    navigate(AppRoute.Artist(artistId))
}
