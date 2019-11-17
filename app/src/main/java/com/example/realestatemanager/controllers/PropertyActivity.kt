package com.example.realestatemanager.controllers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.R
import com.example.realestatemanager.models.Property
import com.example.realestatemanager.ui.property.PropertyFragment

class PropertyActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)

        val property = intent.getParcelableExtra<Property>("property")

        val fragmentB = supportFragmentManager.findFragmentById(R.id.fragmentB) as PropertyFragment?
        fragmentB?.displayDetailsOfGood(property)

    }


}