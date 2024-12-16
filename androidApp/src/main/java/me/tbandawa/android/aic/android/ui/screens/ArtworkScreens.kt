package me.tbandawa.android.aic.android.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import me.tbandawa.android.aic.android.ui.composables.ArtworkDetails
import me.tbandawa.android.aic.android.ui.composables.ArtworkHeader
import me.tbandawa.android.aic.android.ui.composables.ArtworkInfo
import me.tbandawa.android.aic.android.ui.composables.ArtworkToolbar
import me.tbandawa.android.aic.android.ui.composables.ArtworksToolbar
import me.tbandawa.android.aic.android.ui.composables.ItemArtwork
import me.tbandawa.android.aic.android.ui.composables.LoadingData
import me.tbandawa.android.aic.android.ui.composables.LoadingDataError
import me.tbandawa.android.aic.android.ui.composables.LoadingMore
import me.tbandawa.android.aic.android.ui.composables.LoadingMoreError
import me.tbandawa.android.aic.core.ArtworksIntent
import me.tbandawa.android.aic.core.ArtworksState
import me.tbandawa.android.aic.domain.models.Artwork
import me.tbandawa.android.aic.remote.responses.ErrorResponse
import me.tbandawa.android.aic.viewmodels.ArtworkViewModel
import me.tbandawa.android.aic.viewmodels.ArtworksViewModel

class ArtworkScreens(
    private val artworksViewModel: ArtworksViewModel,
    private val artworkViewModel: ArtworkViewModel
) {

    val handleArtworkIntent: (ArtworksIntent) -> Unit = {
        artworksViewModel.handleIntent(it)
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    @Composable
    fun ArtworksScreen(
        navigateToArtwork: (artworkId: Int) -> Unit
    ) {
        val pagingItems = artworksViewModel.pagedArtworks.collectAsLazyPagingItems()
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
                rememberTopAppBarState()
            )

            val pullRefreshState = rememberPullRefreshState(
                refreshing = pagingItems.loadState.refresh is LoadState.Loading && pagingItems.itemSnapshotList.size > 0,
                onRefresh = {
                    pagingItems.refresh()
                }
            )

            Scaffold(
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                containerColor = Color.White,
                topBar = {
                    ArtworksToolbar(title = "Art Institute of Chicago", scrollBehavior = scrollBehavior)
                },
            ) { paddingValues ->
                Box(
                    Modifier
                        .pullRefresh(pullRefreshState)
                        .padding(paddingValues)
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(pagingItems.itemCount) { index ->
                            val artwork = pagingItems[index]!!
                            ItemArtwork(
                                id = artwork.id!!,
                                title = artwork.title,
                                imageId = artwork.imageId,
                                artistDisplay = artwork.artistDisplay,
                                departmentTitle = artwork.departmentTitle
                            ) { artworkId ->
                                navigateToArtwork(artworkId)
                            }
                        }
                        pagingItems.apply {
                            when {
                                loadState.refresh is LoadState.Loading -> {
                                    item { LoadingData() }
                                }
                                loadState.refresh is LoadState.Error -> {
                                    val error = pagingItems.loadState.refresh as LoadState.Error
                                    item { LoadingDataError(message = error.error.message!!) { retry() } }
                                }
                                loadState.append is LoadState.Loading -> {
                                    item { LoadingMore() }
                                }
                                loadState.append is LoadState.Error -> {
                                    val error = pagingItems.loadState.append as LoadState.Error
                                    item { LoadingMoreError(message = error.error.message!!) { retry() } }
                                }
                            }
                        }
                    }
                    PullRefreshIndicator(
                        refreshing = pagingItems.loadState.refresh is LoadState.Loading && pagingItems.itemSnapshotList.size > 0,
                        state = pullRefreshState,
                        scale = true,
                        modifier = Modifier
                            .align(alignment = Alignment.TopCenter)
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ArtworkScreen(
        artworkId: Int,
        navigateBack: () -> Unit
    ) {
        val artworkState = artworkViewModel.state.collectAsState().value

        LaunchedEffect(true) {
            handleArtworkIntent(ArtworksIntent.GetArtwork(id = artworkId))
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {

            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
                rememberTopAppBarState()
            )

            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                containerColor = Color.White,
                topBar = {
                    ArtworkToolbar(navigateBack = navigateBack, scrollBehavior = scrollBehavior)
                },
            ) { paddingValues ->
                when (artworkState) {
                    is ArtworksState.Idle -> {}
                    is ArtworksState.Loading -> {
                        LoadingData()
                    }
                    is ArtworksState.Data -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                                .padding(horizontal = 16.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            val artwork = (artworkState as ArtworksState.Data<*>).data as Artwork
                            ArtworkHeader(
                                image = artwork.imageId!!,
                                title = artwork.title!!,
                                dateDisplay = artwork.dateDisplay,
                                artistDisplay = artwork.artistDisplay,
                                description = artwork.artDescription
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            artwork.artistTitle?.let {
                                ArtworkDetails(title = "Artist", value = it)
                            }
                            artwork.title?.let {
                                ArtworkDetails(title = "Title", value = it)
                            }
                            artwork.placeOfOrigin?.let {
                                ArtworkDetails(title = "Place", value = it)
                            }
                            artwork.dateDisplay?.let {
                                ArtworkDetails(title = "Date", value = it)
                            }
                            artwork.mediumDisplay?.let {
                                ArtworkDetails(title = "Medium", value = it)
                            }
                            artwork.inscriptions?.let {
                                ArtworkDetails(title = "Inscriptions", value = it)
                            }
                            artwork.creditLine?.let {
                                ArtworkDetails(title = "Credit Line", value = it)
                            }
                            artwork.mainReferenceNumber?.let {
                                ArtworkDetails(title = "Reference No.", value = it)
                            }
                            artwork.publicationHistory?.let {
                                ArtworkInfo(title = "PUBLICATION HISTORY", info = it)
                            }
                            artwork.exhibitionHistory?.let {
                                ArtworkInfo(title = "EXHIBITION HISTORY", info = it)
                            }
                            artwork.provenanceText?.let {
                                ArtworkInfo(title = "PROVENANCE", info = it)
                            }
                            artwork.catalogueDisplay?.let {
                                ArtworkInfo(title = "CATALOGUE RAISONNÉS", info = it)
                            }
                        }
                    }
                    is ArtworksState.Error -> {
                        val error = (artworkState as ArtworksState.Error<Any>).data as ErrorResponse
                        LoadingDataError(message = error.detail) {
                            handleArtworkIntent(ArtworksIntent.GetArtwork(id = artworkId))
                        }
                    }
                }
            }
        }
    }

}