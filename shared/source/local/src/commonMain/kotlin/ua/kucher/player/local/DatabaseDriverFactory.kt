package ua.kucher.player.local

import app.cash.sqldelight.db.SqlDriver


internal const val DATABASE_NAME = "KucherPlayerDatabase.db"

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class DatabaseDriverFactory {

    fun createDriver(): SqlDriver
}