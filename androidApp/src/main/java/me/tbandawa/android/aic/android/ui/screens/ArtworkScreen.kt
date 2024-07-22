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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.tbandawa.android.aic.android.ui.composables.ArtworkDetails
import me.tbandawa.android.aic.android.ui.composables.ArtworkHeader
import me.tbandawa.android.aic.android.ui.composables.ArtworkInfo
import me.tbandawa.android.aic.android.ui.composables.ArtworkToolbar
import me.tbandawa.android.aic.android.ui.composables.LoadingData
import me.tbandawa.android.aic.android.ui.composables.LoadingDataError
import me.tbandawa.android.aic.core.ArtworksIntent
import me.tbandawa.android.aic.core.ArtworksResults
import me.tbandawa.android.aic.core.ArtworksState
import me.tbandawa.android.aic.remote.responses.ErrorResponse
import me.tbandawa.android.aic.domain.models.Artwork

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtworkScreen(
    artworkId: Int,
    artworkState: ArtworksState<Artwork>,
    handleIntent: (ArtworksIntent) -> Unit,
    navigateBack: () -> Unit
) {

    LaunchedEffect(true) {
        handleIntent(ArtworksIntent.GetArtwork(id = artworkId))
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
                    val error = (artworkState as ArtworksResults.Error<Any>).data as ErrorResponse
                    LoadingDataError(message = error.detail) {
                        handleIntent(ArtworksIntent.GetArtwork(id = artworkId))
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun ArtworkScreenPreview() {
    ArtworkScreen(
        artworkId = 1,
        artworkState = ArtworksState.Loading,
        handleIntent = {},
        navigateBack = {}
    )
}