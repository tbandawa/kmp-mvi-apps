package me.tbandawa.android.aic.android.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.MutableStateFlow
import me.tbandawa.android.aic.android.ui.composables.ArtworksToolbar
import me.tbandawa.android.aic.android.ui.composables.ItemArtwork
import me.tbandawa.android.aic.android.ui.composables.LoadingData
import me.tbandawa.android.aic.android.ui.composables.LoadingDataError
import me.tbandawa.android.aic.android.ui.composables.LoadingMore
import me.tbandawa.android.aic.android.ui.composables.LoadingMoreError
import me.tbandawa.android.aic.domain.models.Artwork

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ArtworksScreen(
    pagingItems: LazyPagingItems<Artwork>,
    navigateToArtwork: (artworkId: Int) -> Unit
) {

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

@Composable
@Preview
fun ArtworksScreenPreview() {
    val data = emptyList<Artwork>()
    val flow = MutableStateFlow(PagingData.from(data))
    val lazyPagingItems = flow.collectAsLazyPagingItems()
    ArtworksScreen(
        pagingItems = lazyPagingItems,
        navigateToArtwork = {}
    )
}
