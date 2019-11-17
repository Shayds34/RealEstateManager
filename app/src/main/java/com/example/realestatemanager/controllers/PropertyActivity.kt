package com.example.realestatemanager.controllers

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.R
import com.example.realestatemanager.models.Property
import com.example.realestatemanager.ui.property.PropertyFragment

class PropertyActivity : AppCompatActivity(){
    private val myTag = "PropertyActivity"

    lateinit var property: Property

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)

        property = intent.getParcelableExtra("property")

        val fragmentB = supportFragmentManager.findFragmentById(R.id.fragmentB) as PropertyFragment?
        fragmentB?.displayDetailsOfGood(property)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.action_add -> {
            Log.d(myTag, "Action Add")

            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
            finish()

            true
        }
        R.id.action_edit -> {
            Log.d(myTag, "Action Edit")

            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("property", property)
            startActivity(intent)

            finish()

            true
        }
        R.id.action_search -> {
            Log.d(myTag, "Action Search")

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