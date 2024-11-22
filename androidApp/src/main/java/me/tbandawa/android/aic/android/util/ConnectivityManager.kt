package me.tbandawa.android.aic.android.util

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner

class ConnectivityManager (
    application: Application,
) {
    private val connectionLiveData = ConnectionLiveData(application)

    // observe this in ui
    val isNetworkAvailable = mutableStateOf(false)

    fun registerConnectionObserver(lifecycleOwner: LifecycleOwner){
        connectionLiveData.observe(lifecycleOwner, { isConnected ->
            isConnected?.let { isNetworkAvailable.value = it }
        })
    }

    fun unregisterConnectionObserver(lifecycleOwner: LifecycleOwner){
        connectionLiveData.removeObservers(lifecycleOwner)
    }
}