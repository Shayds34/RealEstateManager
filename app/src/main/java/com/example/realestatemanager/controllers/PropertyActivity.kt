package com.example.realestatemanager.controllers

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.R
import com.example.realestatemanager.models.Property
import com.example.realestatemanager.ui.property.PropertyFragment
import kotlinx.android.synthetic.main.fragment_property.*

class PropertyActivity : AppCompatActivity(){
    private val myTag = "PropertyActivity"

    lateinit var property: Property

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)

        property = intent.getParcelableExtra("property")

        val fragmentB = supportFragmentManager.findFragmentById(R.id.fragmentB) as PropertyFragment?
        fragmentB?.displayDetailsOfGood(property)

        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        collapsing_toolbar.title = this.resources.getString(R.string.app_name)
        collapsing_toolbar.setExpandedTitleColor(this.resources.getColor(R.color.transparent))
        collapsing_toolbar.setCollapsedTitleTextColor(Color.rgb(255,255,255))

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        var height = displayMetrics.heightPixels

        collapsing_toolbar.layoutParams.height = height / 3
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    // Test to end PropertyActivity when switching from Portrait to Landscape
    // Because PropertyActivity is not used in landscape mode.
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        finish()
    }
}