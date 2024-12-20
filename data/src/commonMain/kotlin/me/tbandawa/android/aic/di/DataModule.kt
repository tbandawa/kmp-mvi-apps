package me.tbandawa.android.aic.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import me.tbandawa.android.aic.domain.repository.AicRepository
import me.tbandawa.android.aic.remote.api.AicApi
import me.tbandawa.android.aic.remote.mapper.ArtworkMapper
import me.tbandawa.android.aic.remote.repo.AicRepositoryImpl
import me.tbandawa.android.aic.viewmodels.ArtworkViewModel
import me.tbandawa.android.aic.viewmodels.ArtworksViewModel
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

private val mapperModule = module {
    single { ArtworkMapper() }
}

private val repoModule = module {
    single<AicRepository> { AicRepositoryImpl(get(), get(named("IODispatcher")), get()) }
}

private val viewModelModule = module {
    single { ArtworksViewModel(get()) }
    single { ArtworkViewModel(get()) }
}

val modulesList = listOf(
    dispatchersModule,
    apiModule,
    mapperModule,
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
    val artworksViewModel: ArtworksViewModel by inject()
    val artworkViewModel: ArtworkViewModel by inject()
}