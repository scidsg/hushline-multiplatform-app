package org.scidsg.hushline.di

import org.koin.dsl.module
import org.scidsg.hushline.DatabaseBuilder
import org.scidsg.hushline.NetworkHelper
import org.scidsg.hushline.data.database.HushLineDatabase

actual fun platformModule() = module {
    single { NetworkHelper() }
    single { DatabaseBuilder() }
    single<HushLineDatabase> {
        get<DatabaseBuilder>().getDatabase()
    }
}