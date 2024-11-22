package me.tbandawa.android.aic.android

import android.app.Application
import me.tbandawa.android.aic.android.di.appModule
import me.tbandawa.android.aic.di.initKoin
import org.koin.android.ext.koin.androidContext
import timber.log.Timber

class ArtworksApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // start koin
        initKoin {
            androidContext(this@ArtworksApplication)
            modules(listOf(appModule))
        }

        // enable Timber in debug mode
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}