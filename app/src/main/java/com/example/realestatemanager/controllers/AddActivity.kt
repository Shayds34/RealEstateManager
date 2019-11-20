package com.example.realestatemanager.controllers

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.R
import com.example.realestatemanager.models.Property
import com.example.realestatemanager.utils.RealEstateDBHelper
import com.example.realestatemanager.utils.Utils
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity(){
    private val myTag = "AddActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        add_button.setOnClickListener{
            Log.d(myTag, "Add Button clicked.")

            val type = tv_type.text.toString()
            if (type.isEmpty()) tv_type.error = "Required"

            val neighborhood = tv_neighborhood.text.toString()
            if (neighborhood.isEmpty()) tv_neighborhood.error = "Required"

            val price = tv_price.text.toString()
            if (price.isEmpty()) tv_price.error = "Required"

            val size = tv_surface.text.toString()
            if (size.isEmpty()) tv_surface.error = "Required"

            val rooms = tv_room.text.toString()
            if (rooms.isEmpty()) tv_room.error = "Required"

            val bathrooms = tv_bathroom.text.toString()
            if (bathrooms.isEmpty()) tv_bathroom.error = "Required"

            val bedrooms = tv_bedroom.text.toString()
            if (bedrooms.isEmpty()) tv_bedroom.error = "Required"

            val description = tv_description.text.toString()
            if (description.isEmpty()) tv_description.error = "Required"

            val address = tv_address_street.text.toString()
            if (address.isEmpty()) tv_address_street.error = "Required"

            val zipCode = tv_address_zip_code.text.toString()
            if (zipCode.isEmpty()) tv_address_zip_code.error = "Required"

            val city = tv_address_city.text.toString()
            if (city.isEmpty()) tv_address_city.error = "Required"

            val country = tv_address_country.text.toString()
            if (country.isEmpty()) tv_address_country.error = "Required"

            // TODO var photos: List<String>
            // TODO var pointOfInterest: String
            // TODO var status: Boolean
            val creationDate = Utils.getTodayDate().toString()

            // TODO var sellingDate
            val author = tv_author.text.toString()
            if (author.isEmpty()) tv_author.error = "Required"

            val property = Property(
                type,
                neighborhood,
                price,
                size,
                rooms,
                bathrooms,
                bedrooms,
                description,
                address,
                zipCode,
                city,
                country,
                listOf(""),
                "",
                true,
                creationDate,
                "",
                author)

            val dbHelper = RealEstateDBHelper(this, null)
            dbHelper.addProperty(property)
            dbHelper.close()
        }
    }

}