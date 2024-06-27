package org.scidsg.hushline

import dev.tmapps.konnection.Konnection
import dev.tmapps.konnection.NetworkConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

actual class NetworkHelper {

    actual fun isNetworkAvailable(): Boolean {
        val konnection = Konnection.instance
        val result = konnection.isConnected()
        konnection.stop()
        return result
    }
}

//actual fun getNetworkHelper(): NetworkHelper = IOSNetworkHelper()

class NetworkObserver {

    //https://github.com/TM-Apps/konnection

    private var konnection: Konnection = Konnection.instance

    /**
     * Observe connection statuses of any network interface
     */
    fun hasConnectionObservation(scope: CoroutineScope, callback: (Boolean) -> Unit) {
        konnection.observeHasConnection()
            .onEach { callback(it) }
            .launchIn(scope)
    }

    /**
     * Observe connection statuses of the current network
     */
    fun networkConnectionObservation(scope: CoroutineScope, callback: (NetworkConnection) -> Unit) {
        val konnection = Konnection.instance
        konnection.observeNetworkConnection()
            .onEach { it?.let { it1 -> callback(it1) } }
            .launchIn(scope)
    }

    /**
     * Must be called to clear network observation resources on iOS
     */
    fun stop() {
        konnection.stop()
    }

}