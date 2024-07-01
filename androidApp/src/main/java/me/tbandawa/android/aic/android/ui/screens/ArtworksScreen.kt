package me.tbandawa.android.aic.android.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import me.tbandawa.android.aic.android.ui.composables.ArtworksToolbar
import me.tbandawa.android.aic.android.ui.composables.ItemArtwork
import me.tbandawa.android.aic.android.ui.composables.LoadingData
import me.tbandawa.android.aic.android.ui.composables.LoadingDataError
import me.tbandawa.android.aic.android.ui.composables.LoadingMore
import me.tbandawa.android.aic.android.ui.composables.LoadingMoreError
import me.tbandawa.android.aic.lifecycle.ArtworksViewModel
import me.tbandawa.android.aic.remote.responses.Artwork
import org.koin.androidx.compose.inject

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtworksScreen(
    navigateToArtwork: (artworkId: Int) -> Unit
) {

    val viewModel : ArtworksViewModel by inject()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            rememberTopAppBarState()
        )
        val pagingItems: LazyPagingItems<Artwork> = viewModel.pagedArtworks.collectAsLazyPagingItems()

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = Color.White,
            topBar = {
                ArtworksToolbar(title = "Art Institute of Chicago", scrollBehavior = scrollBehavior)
            },
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                items(pagingItems.itemCount) { index ->
                    ItemArtwork(artwork = pagingItems[index]!!) { artworkId ->
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
        }
    }
}