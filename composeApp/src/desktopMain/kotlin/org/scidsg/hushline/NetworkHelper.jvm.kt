package org.scidsg.hushline

import java.net.URL
import java.net.URLConnection

actual class NetworkHelper {

    actual fun isNetworkAvailable(): Boolean {
        try {
            val url: URL = URL("https://hushline.app")
            val connection: URLConnection = url.openConnection()
            connection.connect()
            return true
        } catch (e: Exception) {
            return false
        }
    }
}

//actual fun getNetworkHelper(): NetworkHelper = JVMNetworkHelper()