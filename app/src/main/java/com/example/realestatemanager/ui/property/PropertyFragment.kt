package com.example.realestatemanager.ui.property

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.realestatemanager.R
import com.example.realestatemanager.controllers.EditActivity
import com.example.realestatemanager.models.Property
import com.example.realestatemanager.utils.ImagePagerAdapter
import kotlinx.android.synthetic.main.fragment_property.*

class PropertyFragment : Fragment() {

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

        if(property.creationDate.isNotEmpty()){
            tv_created_date.text = property.creationDate
        }

        // Inflate photos gallery
        val pagerAdapter = ImagePagerAdapter(activity!!.applicationContext, property.photos)
        image_gallery.adapter = pagerAdapter

        val geoLocUri = "https://i.goopics.net/OQLPN.png"
        Glide.with(this)
            .load(geoLocUri)
            .centerCrop()
            .into(geo_location_image)

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
}