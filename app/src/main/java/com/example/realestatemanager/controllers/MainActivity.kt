package com.example.realestatemanager.controllers

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.R
import com.example.realestatemanager.models.Property
import com.example.realestatemanager.ui.property.PropertyFragment
import com.example.realestatemanager.utils.Communicator
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), Communicator {
    private val myTag = "MainActivity"

    private var isDualPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.app_name)

        // Check that the activity is using the layout version landscape
        val propertyFragmentView = findViewById<View>(R.id.fragmentB)
        isDualPane = propertyFragmentView?.visibility == View.VISIBLE

        }

    override fun displayDetailsOfGood(property: Property) {
        Log.d(myTag, "displayDetailsOfGood")
        if (isDualPane){
            val propertyFragment = supportFragmentManager.findFragmentById(R.id.fragmentB) as PropertyFragment?
            propertyFragment?.displayDetailsOfGood(property)
        } else {
            val intent = Intent(this, PropertyActivity::class.java)
            intent.putExtra("property", property)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.action_add -> {
            Log.d(myTag, "Action Add")

            true
        }
        R.id.action_edit -> {
            Log.d(myTag, "Action Edit")

            true
        }
        R.id.action_search -> {
            Log.d(myTag, "Action Search")

            true
        }

        else -> super.onOptionsItemSelected(item)
    }



}


