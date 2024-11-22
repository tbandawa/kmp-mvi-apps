package me.tbandawa.android.aic.android.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import me.tbandawa.android.aic.android.MyApplicationTheme
import me.tbandawa.android.aic.android.ui.navigation.NavGraph

class MainActivity : ComponentActivity() {

    /*override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MyApplicationTheme {
                NavGraph()
            }
        }
    }
}
