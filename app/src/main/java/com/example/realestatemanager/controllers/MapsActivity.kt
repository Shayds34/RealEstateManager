package com.example.realestatemanager.controllers

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.realestatemanager.R
import com.example.realestatemanager.models.Property
import com.example.realestatemanager.utils.RealEstateDBHelper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import java.io.IOException
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    //#region {Initialization}
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation : Location

    private lateinit var propertiesList: ArrayList<Property>
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        // To get last known location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Get the data from database to create map and markers.
        val dbHelper = RealEstateDBHelper(this, null)
        propertiesList = dbHelper.getListOfProperties()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        // Once Map is ready, we can populate it with markers.
        setUpMap()
    }

    private fun setUpMap() {
        // Check Location Permission
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            Log.d("MapsActivity", "Asking for permission.")
            return
        }

        // Enable self location dot
        map.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(propertiesList)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12.0f))
            }
        }
    }

    private fun placeMarkerOnMap (propertiesData: ArrayList<Property>) {
        try {
            // Clear all previous markers.
            map.clear()

            map.uiSettings.isMapToolbarEnabled = true

            // For each property in properties data, create new Marker
            // And pass "property" to the tag to enable
            // ClickListener to pass it by extras to PropertyActivity
            for (property in propertiesData) {
                val propertyAddress = property.address.plus(" ").plus(property.city)
                val propertyLocation = getLocationFromAddress(this, propertyAddress)

                val mMarker = map.addMarker(propertyLocation?.let {
                    MarkerOptions()
                        .position(it)
                        .title(property.type)
                        .snippet(property.description)
                })
                mMarker.tag = property
                Log.d("MapsActivity", "Marker tag is ${mMarker.tag} & current property is $property")

                map.setOnInfoWindowClickListener {marker ->
                    Log.d("MapsActivity", "Marker tag is ${marker.tag} & current property is $property")
                    val currentProperty = marker.tag as Property

                    val intent = Intent(this, PropertyActivity::class.java)
                    intent.putExtra("property", currentProperty)
                    startActivity(intent)
                }
            }
        } catch (e : IOException) {
            e.printStackTrace()
        }
    }

    private fun getLocationFromAddress(context: Context, strAddress: String) : LatLng? {
        // Get LatLng from address string with Geocoder and return it as propertyLatLng
        val geoCoder = Geocoder(context, Locale.getDefault())
        val address : List<Address>
        var propertyLatLng : LatLng? = null

        try {
            address = geoCoder.getFromLocationName(strAddress, 5)
            val mLocation = address[0]
            propertyLatLng = LatLng(mLocation.latitude, mLocation.longitude)
        } catch (e : IOException) {
            e.printStackTrace()
        }

        return propertyLatLng
    }

    override fun onMarkerClick(p0: Marker?) = false

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}