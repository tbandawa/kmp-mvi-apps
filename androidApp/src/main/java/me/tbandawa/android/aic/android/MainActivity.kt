package me.tbandawa.android.aic.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import me.tbandawa.android.aic.lifecycle.ArtworksAction
import me.tbandawa.android.aic.lifecycle.ArtworksState
import me.tbandawa.android.aic.lifecycle.ArtworksViewModel
import me.tbandawa.android.aic.remote.responses.Artwork
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val artworksViewModel: ArtworksViewModel = koinViewModel()
                    val pagingItems: LazyPagingItems<Artwork> = artworksViewModel.pagedArtworks.collectAsLazyPagingItems()

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        item {
                            Spacer(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .background(Color.Green)
                            )
                        }
                        items(pagingItems.itemCount) { index ->
                            Row(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .fillParentMaxWidth()
                            ) {
                                Text(text = pagingItems[index]?.title!!)
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
    }
}

@Composable
fun ArtworksView() {

    val artworksViewModel: ArtworksViewModel = koinViewModel()
    val userState by artworksViewModel.state.collectAsState()
    var message by remember { mutableStateOf("") }

    Column {
        message = when (userState) {
            is ArtworksState.Idle -> {
                "Idle"
            }

            is ArtworksState.Loading -> {
                "Loading..."
            }

            is ArtworksState.Success -> {
                "Success"
            }

            is ArtworksState.Error -> {
                "Error!"
            }
        }
        Button(onClick = {
            artworksViewModel.handleAction(ArtworksAction.GetArtwork(id = 13527))
        }) {
            Text(text = "Get Artwork")
        }
        Text(text = userState.toString())
    }
}
