package me.tbandawa.android.aic.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import me.tbandawa.android.aic.lifecycle.ArtworksViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MyApplicationTheme {
                val artworksViewModel: ArtworksViewModel = koinViewModel()
                ArtworksView(viewModel = artworksViewModel)
            }
        }
    }
}
