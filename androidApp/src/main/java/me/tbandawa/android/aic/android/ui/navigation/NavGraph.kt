package me.tbandawa.android.aic.android.ui.navigation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import me.tbandawa.android.aic.android.ui.screens.ArtworkScreens
import me.tbandawa.android.aic.android.util.ConnectivityManager
import org.koin.androidx.compose.inject

@Composable
fun NavGraph() {

    val connectivityManager: ConnectivityManager by inject()
    val screens : ArtworkScreens by inject()

    val navController = rememberNavController()
    var isNetworkBanner by remember { mutableStateOf(false) }

    LifecycleStartEffect(Unit) {
        connectivityManager.registerConnectionObserver(this)
        onStopOrDispose {
            connectivityManager.unregisterConnectionObserver(this)
        }
    }

    connectivityManager.isNetworkAvailable.value.also {
        if (it) {
            LaunchedEffect(Unit) {
                delay(2000)
                isNetworkBanner = false
            }
        } else {
            isNetworkBanner = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // Navigation host
        NavHost(
            modifier = Modifier
                .weight(1f),
            navController = navController,
            startDestination = "artworks"
        ) {
            composable(route = "artworks") {
                screens.ArtworksScreen(
                    pagingItems = screens.getPagingItems(),
                    navigateToArtwork = { artworkId ->
                        navController.navigateToArtwork(artworkId)
                    }
                )
            }
            composable(route = "artwork/{artworkId}") { backStackEntry ->
                val artworkId = requireNotNull(backStackEntry.arguments?.getString("artworkId")) {
                    "Artwork Identifier in missing"
                }
                screens.ArtworkScreen(
                    artworkId = artworkId.toInt(),
                    artworkState = screens.getArtworkState(),
                    handleIntent = { artworksIntent ->
                        screens.handleArtworkIntent(artworksIntent)
                    },
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }

        // Network availability banner
        Row(
            modifier = Modifier
                .background(if (!connectivityManager.isNetworkAvailable.value) Color.DarkGray else Color(0xff00cf7c))
                .fillMaxWidth()
                .animateContentSize()
                .height(if (isNetworkBanner) 25.dp else 0.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (!connectivityManager.isNetworkAvailable.value) "No Internet Connection" else "Internet Connection Available",
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .wrapContentSize()
            )
        }
    }
}

fun NavController.navigateToArtwork(artworkId: Int) {
    if (this.currentDestination?.route !== "artwork/$artworkId") {
        navigate(route = "artwork/$artworkId")
    }
}