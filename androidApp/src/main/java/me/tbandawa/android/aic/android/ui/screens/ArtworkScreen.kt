package me.tbandawa.android.aic.android.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import me.tbandawa.android.aic.android.ui.composables.ArtworkDetails
import me.tbandawa.android.aic.android.ui.composables.ArtworkHeader
import me.tbandawa.android.aic.android.ui.composables.ArtworkInfo
import me.tbandawa.android.aic.android.ui.composables.ArtworkToolbar
import me.tbandawa.android.aic.android.ui.composables.LoadingData
import me.tbandawa.android.aic.android.ui.composables.LoadingDataError
import me.tbandawa.android.aic.lifecycle.ArtworksIntent
import me.tbandawa.android.aic.lifecycle.ArtworksState
import me.tbandawa.android.aic.lifecycle.ArtworksViewModel
import me.tbandawa.android.aic.remote.responses.ArtworkResponse
import me.tbandawa.android.aic.remote.responses.ErrorResponse
import org.koin.androidx.compose.inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtworkScreen(
    artworkId: Int,
    navigateBack: () -> Unit
) {

    val viewModel : ArtworksViewModel by inject()

    LaunchedEffect(true) {
        viewModel.handleIntent(ArtworksIntent.GetArtwork(id = artworkId))
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            rememberTopAppBarState()
        )
        val artworkState = viewModel.state.collectAsState()

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = Color.White,
            topBar = {
                ArtworkToolbar(navigateBack = navigateBack, scrollBehavior = scrollBehavior)
            },
        ) { paddingValues ->
            when (artworkState.value) {
                is ArtworksState.Idle -> {}
                is ArtworksState.Loading -> {
                    LoadingData()
                }
                is ArtworksState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(horizontal = 16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        val artwork = (artworkState.value as ArtworksState.Success<*>).data as ArtworkResponse
                        ArtworkHeader(
                            image = artwork.data.imageId!!,
                            title = artwork.data.title!!,
                            dateDisplay = artwork.data.dateDisplay,
                            artistDisplay = artwork.data.artistDisplay,
                            description = artwork.data.artDescription
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        artwork.data.artistTitle?.let {
                            ArtworkDetails(title = "Artist", value = it)
                        }
                        artwork.data.title?.let {
                            ArtworkDetails(title = "Title", value = it)
                        }
                        artwork.data.placeOfOrigin?.let {
                            ArtworkDetails(title = "Place", value = it)
                        }
                        artwork.data.dateDisplay?.let {
                            ArtworkDetails(title = "Date", value = it)
                        }
                        artwork.data.mediumDisplay?.let {
                            ArtworkDetails(title = "Medium", value = it)
                        }
                        artwork.data.inscriptions?.let {
                            ArtworkDetails(title = "Inscriptions", value = it)
                        }
                        artwork.data.creditLine?.let {
                            ArtworkDetails(title = "Credit Line", value = it)
                        }
                        artwork.data.mainReferenceNumber?.let {
                            ArtworkDetails(title = "Reference. No.", value = it)
                        }
                        artwork.data.publicationHistory?.let {
                            ArtworkInfo(title = "PUBLICATION HISTORY", info = it)
                        }
                        artwork.data.exhibitionHistory?.let {
                            ArtworkInfo(title = "EXHIBITION HISTORY", info = it)
                        }
                        artwork.data.provenanceText?.let {
                            ArtworkInfo(title = "PROVENANCE", info = it)
                        }
                        artwork.data.catalogueDisplay?.let {
                            ArtworkInfo(title = "CATALOGUE RAISONNÉS", info = it)
                        }
                    }
                }
                is ArtworksState.Error -> {
                    val error = (artworkState.value as ArtworksState.Error<Any>).data as ErrorResponse
                    LoadingDataError(message = error.detail) {
                        viewModel.handleIntent(ArtworksIntent.GetArtwork(id = artworkId))
                    }
                }
            }
        }
    }
}