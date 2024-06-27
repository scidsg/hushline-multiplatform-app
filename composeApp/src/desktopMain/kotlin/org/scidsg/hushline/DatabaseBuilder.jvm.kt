package org.scidsg.hushline

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.scidsg.hushline.data.database.HushLineDatabase
import java.io.File

actual class DatabaseBuilder {

    actual fun getDatabase(): HushLineDatabase {
        val dbFile = File(System.getProperty("java.io.tmpdir"), "hushline.db")
        return Room.databaseBuilder<HushLineDatabase>(
            name = dbFile.absolutePath,
        ).addMigrations()
        .fallbackToDestructiveMigrationOnDowngrade(true) //TODO proper migration
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
    }
}
