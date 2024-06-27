package org.scidsg.hushline.data.database

//import kotlin.jvm.Volatile
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [UserEntity::class, MessageEntity::class, JWTTokenEntity::class], version = 1)
abstract class HushLineDatabase: RoomDatabase()/*, DB*/ {

    abstract fun userDao(): UserDao

    abstract fun messageDao(): MessageDao

    abstract fun tokenDao(): JWTTokenDao

    /*override fun clearAllTables() {
        super.clearAllTables()
    }*/

    /*companion object {
        fun getHushLineDatabase(builder: Builder<HushLineDatabase>): HushLineDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            //synchronized(this) { //TODO: implement this lock properly
            return builder
                .addMigrations()
                .fallbackToDestructiveMigrationOnDowngrade(true) //TODO proper migration
                .setDriver(BundledSQLiteDriver())
                .setQueryCoroutineContext(Dispatchers.IO)
                .build()
            //}
        }
    }*/
}

// FIXME: Added a hack to resolve below issue:
// Class 'HushLineDatabase_Impl' is not abstract and does not implement abstract base class member 'clearAllTables'.
interface DB {
    fun clearAllTables() {}
}