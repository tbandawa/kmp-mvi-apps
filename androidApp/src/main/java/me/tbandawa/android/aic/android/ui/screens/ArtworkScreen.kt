package me.tbandawa.android.aic.android.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import me.tbandawa.android.aic.android.ui.composables.ArtworkToolbar
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
        modifier = Modifier.fillMaxSize()
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
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                when (artworkState.value) {
                    is ArtworksState.Idle -> {
                        Text(text = "Idle State")
                    }
                    is ArtworksState.Loading -> {
                        Text(text = "Loading State")
                    }
                    is ArtworksState.Success -> {
                        val artwork = (artworkState.value as ArtworksState.Success<ArtworkResponse>).data
                        Text(text = "Success => ${artwork.data.title}")
                    }
                    is ArtworksState.Error -> {
                        val error = (artworkState.value as ArtworksState.Error<Any>).data as ErrorResponse
                        Text(text = "Error => ${error.detail}")
                    }
                }
            }
        }
    }
}