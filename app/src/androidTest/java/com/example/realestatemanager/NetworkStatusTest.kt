package com.example.realestatemanager

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.realestatemanager.utils.Utils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class NetworkStatusTest {

    private lateinit var appContext : Context
    private lateinit var manager : ConnectivityManager
    private lateinit var utils : Utils
    private var isConnected : Boolean = false

    @Before
    fun setUp() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        manager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        utils = Utils.getInstance(appContext)
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        assertNotNull(appContext)
    }

    @Test
    fun useManager() {
        // Manager
        assertNotNull(manager)
    }

    @Test
    fun getWifiStatus() {
        val wifi : NetworkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        isConnected = if (wifi.detailedState == NetworkInfo.DetailedState.CONNECTED) {
            true
        } else {
            wifi.detailedState == NetworkInfo.DetailedState.CONNECTING
        }
        println("WIFI status: ${wifi.detailedState}, Result from method: ${utils.isInternetAvailable(appContext)}")
        assertEquals(isConnected, utils.isInternetAvailable(appContext))
    }

    @Test
    fun getNetworkInfo_shouldReturnDefaultNetworks() {
        val wifi : NetworkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobile : NetworkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        isConnected = if (wifi.detailedState == NetworkInfo.DetailedState.CONNECTED) {
            true
        } else {
            mobile.detailedState == NetworkInfo.DetailedState.CONNECTED
        }

        println("WIFI status: ${wifi.detailedState}, MOBILE status: ${mobile.detailedState}, Result from method: ${utils.isOnline(appContext)}.")
        assertEquals(isConnected, utils.isOnline(appContext))
    }
}
