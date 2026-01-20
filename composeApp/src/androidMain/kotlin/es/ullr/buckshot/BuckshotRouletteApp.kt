package es.ullr.buckshot

import android.app.Application
import es.ullr.buckshot.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class BuckshotRouletteApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@BuckshotRouletteApp)
        }
    }
}