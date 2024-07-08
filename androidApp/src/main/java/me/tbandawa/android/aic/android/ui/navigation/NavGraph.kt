package me.tbandawa.android.aic.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.tbandawa.android.aic.android.ui.screens.ArtworkScreen
import me.tbandawa.android.aic.android.ui.screens.ArtworksScreen
import me.tbandawa.android.aic.lifecycle.ArtworkViewModel
import me.tbandawa.android.aic.lifecycle.ArtworksState
import me.tbandawa.android.aic.lifecycle.State
import me.tbandawa.android.aic.remote.responses.ArtworkResponse
import org.koin.androidx.compose.inject

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "artworks"
    ) {
        composable(route = "artworks") {
            ArtworksScreen {
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
}

fun NavController.navigateToArtwork(artworkId: Int) {
    if (this.currentDestination?.route !== "artwork/$artworkId") {
        navigate(route = "artwork/$artworkId")
    }
}