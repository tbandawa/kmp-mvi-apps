package me.tbandawa.android.aic.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import me.tbandawa.android.aic.remote.api.AicApi
import me.tbandawa.android.aic.remote.repo.AicRepository
import me.tbandawa.android.aic.remote.repo.AicRepositoryImpl
import me.tbandawa.android.aic.lifecycle.ArtworksViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val dispatchersModule = module {
    single(named("IODispatcher")) {
        Dispatchers.IO
    }
    single(named("MainDispatcher")) {
        Dispatchers.Main
    }
}

private val apiModule = module {
    single { AicApi() }
}

private val repoModule = module {
    single<AicRepository> { AicRepositoryImpl(get(), get(named("IODispatcher"))) }
}

private val viewModelModule = module {
    single { ArtworksViewModel(get()) }
}

val modulesList = listOf(
    dispatchersModule,
    apiModule,
    repoModule,
    viewModelModule
)

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(modulesList)
}

fun initKoin(){
    startKoin {
        modules(modulesList)
    }
}

class DataHelper: KoinComponent {
    val viewModel: ArtworksViewModel by inject()
}