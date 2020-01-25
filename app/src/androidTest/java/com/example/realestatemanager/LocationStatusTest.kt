package com.example.realestatemanager

import android.content.Context
import android.location.LocationManager
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
class LocationStatusTest {

    private lateinit var appContext : Context
    private lateinit var locationManager : LocationManager
    private lateinit var utils : Utils
    private var gpsEnabled = false
    private var networkEnabled = false

    @Before
    fun setUp() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        locationManager = appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

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
        assertNotNull(locationManager)
    }

    @Test
    fun getLocationProviders_shouldReturnDefaultProviders() {

        try {
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        println("GPS status: ${gpsEnabled}, NETWORK status: ${networkEnabled}, Result from method: ${utils.locationServicesEnabled(appContext)}.")
        assertEquals(gpsEnabled , utils.locationServicesEnabled(appContext))
    }
}
