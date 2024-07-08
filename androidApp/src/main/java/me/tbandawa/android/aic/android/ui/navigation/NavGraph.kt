package me.tbandawa.android.aic.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.tbandawa.android.aic.android.ui.screens.ArtworkScreen
import me.tbandawa.android.aic.android.ui.screens.ArtworksScreen

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
            ArtworkScreen(artworkId = artworkId.toInt()) {
                navController.navigateUp()
            }
        }
    }
}

fun NavController.navigateToArtwork(artworkId: Int) {
    if (this.currentDestination?.route !== "artwork/$artworkId") {
        navigate(route = "artwork/$artworkId")
    }
}