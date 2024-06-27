package org.scidsg.hushline.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    additionalModule: List<Module> = emptyList(),
    appDeclaration: KoinAppDeclaration = {}
) = startKoin {
    appDeclaration()
    modules(
        additionalModule +
                listOf(
                    platformModule(),
                    commonModule(),
                    ),
        )
}.koin