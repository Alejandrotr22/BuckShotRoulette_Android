package es.ullr.buckshot.di

import es.ullr.buckshot.domain.GameModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::GameModel)
}