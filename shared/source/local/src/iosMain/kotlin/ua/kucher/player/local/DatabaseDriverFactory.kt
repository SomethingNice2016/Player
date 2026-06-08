package ua.kucher.player.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import ua.kucher.player.database.KucherPlayerDatabase

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class DatabaseDriverFactory{

    actual fun createDriver(): SqlDriver =
        NativeSqliteDriver(KucherPlayerDatabase.Schema, DATABASE_NAME)
}