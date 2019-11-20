package com.example.realestatemanager.controllers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.R
import com.example.realestatemanager.models.Property
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    lateinit var property: Property

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        property = intent.getParcelableExtra("property")

        tv_description.hint = property.description
        tv_surface.hint = property.size.toString()
        tv_room.hint = property.rooms.toString()
        // tv_bathroom.hint = property.bathrooms.toString()
        // tv_bedroom.hint = property.bedroom.toString()
        tv_address_street.hint = property.address
        tv_address_zip_code.hint = property.zip_code
        tv_address_city.hint = property.city
        tv_address_country.hint = property.country



    }

}