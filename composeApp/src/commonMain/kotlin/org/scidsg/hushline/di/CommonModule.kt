package org.scidsg.hushline.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.scidsg.hushline.DatabaseBuilder
import org.scidsg.hushline.data.database.HushLineDatabase
import org.scidsg.hushline.data.MessageRepository
import org.scidsg.hushline.data.UserRepository
import org.scidsg.hushline.ui.landing.ForgotPasswordViewModel
import org.scidsg.hushline.ui.landing.LoginViewModel
import org.scidsg.hushline.ui.landing.RegisterViewModel

fun commonModule() = module {
    /*single<HushLineDatabase> {
        HushLineDatabase.getHushLineDatabase(
            get<DatabaseBuilder>().getDatabase()
        )
    }*/
    single {
        HttpClient() {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Logging)
        }
    }

    single { get<HushLineDatabase>().userDao() }
    single { UserRepository(get(), get()) }
    single { MessageRepository(get()) }
    single { LoginViewModel(get()) }
    single { RegisterViewModel(get()) }
    single { ForgotPasswordViewModel(get()) }
}