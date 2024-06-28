package me.tbandawa.android.aic.android

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import me.tbandawa.android.aic.lifecycle.ArtworksViewModel
import me.tbandawa.android.aic.remote.responses.Artwork
import timber.log.Timber

@Composable
fun ArtworksView(
    viewModel: ArtworksViewModel
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        val pagingItems: LazyPagingItems<Artwork> = viewModel.pagedArtworks.collectAsLazyPagingItems()

        LazyColumn(
            modifier = Modifier.wrapContentHeight()
        ) {
            items(pagingItems.itemCount) { index ->
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .fillParentMaxWidth()
                ) {
                    ItemArtwork(artwork = pagingItems[index]!!) { artworkId ->
                        Timber.d("artwork id = $artworkId")
                    }
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