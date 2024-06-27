package org.scidsg.hushline

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.scidsg.hushline.data.database.HushLineDatabase

actual class DatabaseBuilder(private val context: Context) {

    actual fun getDatabase(): HushLineDatabase {

        val dbFile = context.getDatabasePath("hushline.db")
        return Room.databaseBuilder<HushLineDatabase>(
            context = context,
            name = dbFile.absolutePath
        ).addMigrations()
        .fallbackToDestructiveMigrationOnDowngrade(true) //TODO proper migration
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
    }
}