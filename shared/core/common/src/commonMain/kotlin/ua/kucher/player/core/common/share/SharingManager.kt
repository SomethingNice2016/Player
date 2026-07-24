package ua.kucher.player.core.common.share

import okio.Path

interface SharingManager {

    fun shareUri(uri: String)

    fun shareFile(path: Path)

}