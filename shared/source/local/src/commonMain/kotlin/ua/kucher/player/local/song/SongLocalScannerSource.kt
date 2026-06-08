package ua.kucher.player.local.song

import ua.kucher.player.database.SongEntity

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class SongLocalScannerSource {

    fun getSongs(): List<SongEntity>

}