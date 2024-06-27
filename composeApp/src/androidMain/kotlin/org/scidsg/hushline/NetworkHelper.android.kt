package org.scidsg.hushline

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

actual class NetworkHelper(private val context: Context) {

    actual fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}

//actual fun getNetworkHelper(): NetworkHelper = AndroidNetworkHelper()