package me.tbandawa.android.aic.android.di

import me.tbandawa.android.aic.android.ui.screens.ArtworkScreens
import me.tbandawa.android.aic.android.util.ConnectivityManager
import org.koin.dsl.module

val appModule = module {
    single { ConnectivityManager(get()) }
    single { ArtworkScreens(get(), get())}
}