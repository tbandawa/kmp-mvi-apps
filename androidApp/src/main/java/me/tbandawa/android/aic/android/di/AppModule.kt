package me.tbandawa.android.aic.android.di

import me.tbandawa.android.aic.android.util.ConnectivityManager
import org.koin.dsl.module

val appModule = module {
    factory { ConnectivityManager(get()) }
}