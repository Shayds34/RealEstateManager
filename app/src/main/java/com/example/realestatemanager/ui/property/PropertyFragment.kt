package com.example.realestatemanager.ui.property

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.realestatemanager.R
import com.example.realestatemanager.controllers.EditActivity
import com.example.realestatemanager.models.Property
import com.example.realestatemanager.utils.ImagePagerAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_property.*
import java.io.IOException
import java.util.*

class PropertyFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mProperty: Property

    companion object {
        fun newInstance(property: Property): PropertyFragment {
            val fragment = PropertyFragment()
            val args = Bundle()
            args.putParcelable("property", property)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment.
        return inflater.inflate(R.layout.fragment_property, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mapView != null){
            mapView.onCreate(savedInstanceState)
            mapView.getMapAsync(this)
        }
    }

    override fun onStart() {
        super.onStart()

        // During start, check if there are arguments passed to the fragment.
        // onStart is a good neighborhood to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the property info
        val bundle: Bundle? = arguments
        if (bundle != null){
            // Set property based on arguments passed in
            displayDetailsOfGood(bundle.get("property") as Property)
        }
    }

    fun displayDetailsOfGood(property : Property){
        mProperty = property

        // Set all texts according to database
        tv_description.text = property.description
        tv_type.text = property.type

        // TODO currency and format price
        tv_price.text = property.price

        tv_surface.text = property.size
        tv_room.text = property.rooms
        tv_bathroom.text = property.bathrooms
        tv_bedroom.text = property.bedrooms
        tv_address_street.text = property.address
        tv_address_zip_code.text = property.zip_code
        tv_address_city.text = property.city
        tv_address_country.text = property.country
        tv_author.text = "Created by ${property.author}"

        if (property.photos.size > 1) {
            image_number.text = property.photos.size.toString().plus(" photos")
        } else {
            image_number.text =     "1 photo"
        }


        if(property.creationDate.isNotEmpty()){
            tv_created_date.text = property.creationDate
        }

        // Inflate photos gallery
        val pagerAdapter = ImagePagerAdapter(activity!!.applicationContext, property.photos)
        image_gallery.adapter = pagerAdapter
        circle_indicator.setViewPager(image_gallery)


        //#region {Floating Edit Button}
        floating_button_edit.setOnClickListener {
            val intent = Intent(activity!!.applicationContext, EditActivity::class.java)
            intent.putExtra("property", property)
            startActivity(intent)
        }
        //endregion
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save current currentProperty selection in case we need to recreate the fragment
        //
        // outState.putParcelable("currentProperty", currentProperty)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        MapsInitializer.initialize(activity!!.applicationContext)
        mMap = googleMap
        placeLiteModeMarker()
    }

    private fun placeLiteModeMarker() {
        val propertyAddress = mProperty.address.plus(" ").plus(mProperty.city).plus(" ").plus(mProperty.zip_code)
        val propertyLocation = getLocationFromAddress(activity!!.applicationContext, propertyAddress)

        val markerOptions = propertyLocation?.let {
            MarkerOptions()
                .position(it)
                .title(mProperty.type)
                .snippet(mProperty.description)
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(propertyLocation, 15.0f))
        mMap.addMarker(markerOptions)
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mapView.onResume()
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
}