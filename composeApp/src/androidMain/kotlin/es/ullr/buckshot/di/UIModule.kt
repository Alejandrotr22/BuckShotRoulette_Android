package es.ullr.buckshot.di

import es.ullr.buckshot.ui.game.GameViewModel
import org.koin.core.module.dsl.viewModelOf


import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::GameViewModel)
}