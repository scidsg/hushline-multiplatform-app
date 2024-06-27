package org.scidsg.hushline

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.scidsg.hushline.di.androidModule
import org.scidsg.hushline.di.initKoin

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(additionalModule = listOf(androidModule())) {
            androidContext(this@Application)
            androidLogger()
            //modules(listOf(androidModule()))
        }
    }
}