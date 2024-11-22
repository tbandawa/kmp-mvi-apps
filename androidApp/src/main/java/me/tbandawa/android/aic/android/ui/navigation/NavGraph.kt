package me.tbandawa.android.aic.android.ui.navigation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import me.tbandawa.android.aic.android.ui.screens.ArtworkScreen
import me.tbandawa.android.aic.android.ui.screens.ArtworksScreen
import me.tbandawa.android.aic.android.util.ConnectivityManager
import me.tbandawa.android.aic.domain.models.Artwork
import me.tbandawa.android.aic.viewmodels.ArtworkViewModel
import me.tbandawa.android.aic.viewmodels.ArtworksViewModel
import org.koin.androidx.compose.inject

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    val connectivityManager: ConnectivityManager by inject()

    LifecycleStartEffect(Unit) {
        connectivityManager.registerConnectionObserver(this)
        onStopOrDispose {
            connectivityManager.unregisterConnectionObserver(this)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        NavHost(
            modifier = Modifier
                .weight(1f),
            navController = navController,
            startDestination = "artworks"
        ) {
            composable(route = "artworks") {
                val viewModel : ArtworksViewModel by inject()
                val pagingItems: LazyPagingItems<Artwork> = viewModel.pagedArtworks.collectAsLazyPagingItems()
                ArtworksScreen(
                    pagingItems = pagingItems
                ) {
                    navController.navigateToArtwork(it)
                }
            }
            composable(route = "artwork/{artworkId}") { backStackEntry ->
                val artworkId = requireNotNull(backStackEntry.arguments?.getString("artworkId")) {
                    "Artwork Identifier in missing"
                }
                val viewModel : ArtworkViewModel by inject()
                val artworkState = viewModel.state.collectAsState().value
                ArtworkScreen(
                    artworkId = artworkId.toInt(),
                    artworkState = artworkState,
                    handleIntent = { artworksIntent ->
                        viewModel.handleIntent(artworksIntent)
                    },
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }

        Row(
            modifier = Modifier
                .background(Color.DarkGray)
                .animateContentSize()
                .height(if (!connectivityManager.isNetworkAvailable.value) 45.dp else 0.dp)
                .fillMaxWidth()
                .height(45.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "No Internet Connection",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

    }

}

fun NavController.navigateToArtwork(artworkId: Int) {
    if (this.currentDestination?.route !== "artwork/$artworkId") {
        navigate(route = "artwork/$artworkId")
    }
}