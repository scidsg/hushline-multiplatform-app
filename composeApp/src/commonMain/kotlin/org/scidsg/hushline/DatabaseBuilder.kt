package org.scidsg.hushline

import org.scidsg.hushline.data.database.HushLineDatabase

expect class DatabaseBuilder {
    fun getDatabase(): HushLineDatabase
}