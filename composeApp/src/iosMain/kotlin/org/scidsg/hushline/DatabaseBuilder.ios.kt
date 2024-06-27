package org.scidsg.hushline

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import instantiateImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.scidsg.hushline.data.database.HushLineDatabase
import platform.Foundation.NSHomeDirectory

actual class DatabaseBuilder {

    actual fun getDatabase(): HushLineDatabase {
        val dbFilePath = NSHomeDirectory() + "/hushline.db"

        return Room.databaseBuilder<HushLineDatabase>(
            name = dbFilePath,
            factory = { HushLineDatabase::class.instantiateImpl() }
        ).addMigrations()
        .fallbackToDestructiveMigrationOnDowngrade(true) //TODO proper migration
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
    }
}