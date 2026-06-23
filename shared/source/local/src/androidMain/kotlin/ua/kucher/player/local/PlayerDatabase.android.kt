package ua.kucher.player.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase


internal fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<PlayerDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(DATABASE_NAME)

    return Room.databaseBuilder<PlayerDatabase>(
        context = appContext,
        name = dbFile.absolutePath,
    )
}