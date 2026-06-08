package ua.kucher.player.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import ua.kucher.player.database.KucherPlayerDatabase

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class DatabaseDriverFactory(
    private val context: Context
) {

    actual fun createDriver(): SqlDriver = AndroidSqliteDriver(
        schema = KucherPlayerDatabase.Schema,
        context = context,
        name = DATABASE_NAME,
    )
}