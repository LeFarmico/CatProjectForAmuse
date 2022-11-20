package io.amuse.codeassignment.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

enum class InternetStatus {
    Available, Unavailable, Undefined
}

val Context.currentInternetState: InternetStatus
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return getCurrentConnectivityState(connectivityManager)
    }

private fun getCurrentConnectivityState(
    connectivityManager: ConnectivityManager
): InternetStatus {
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
        return InternetStatus.Undefined
    }
    val activeNetwork = connectivityManager.activeNetwork
    val connected = connectivityManager.getNetworkCapabilities(activeNetwork)
        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        ?: false

    return if (connected) InternetStatus.Available else InternetStatus.Unavailable
}

@ExperimentalCoroutinesApi
fun Context.observeInternetStateAsFlow() = callbackFlow {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val callback = networkCallback { connectionState -> trySend(connectionState) }

    // Request for checking network connection
    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    connectivityManager.registerNetworkCallback(networkRequest, callback)

    // initialize current state
    val currentState = getCurrentConnectivityState(connectivityManager)
    trySend(currentState)

    // unregister callback after no need
    awaitClose {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}

fun networkCallback(callback: (InternetStatus) -> Unit): ConnectivityManager.NetworkCallback {
    return object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            callback(InternetStatus.Available)
        }

        override fun onLost(network: Network) {
            callback(InternetStatus.Unavailable)
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun internetState(): State<InternetStatus> {
    val context = LocalContext.current
    return produceState(initialValue = context.currentInternetState) {
        context.observeInternetStateAsFlow().collect { value = it }
    }
}
