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
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), Communicator {
    private val myTag = "MainActivity"

    private var isDualPane = false

    lateinit var mProperty: Property

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

        mProperty = property

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

            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)

            true
        }
        R.id.action_edit -> {
            Log.d(myTag, "Action Edit")

            // Check if user is in portrait/landscape version
            // Check if user has chosen an item from list
            if (isDualPane){
                // Landscape
                Log.d(myTag, "Landscape.")

                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("property", mProperty)
                startActivity(intent)
            } else {
                // Portrait
                Log.d(myTag, "Portrait.")

                if (fragment_container != null){
                    Snackbar.make(fragment_container, "You have to select a property to edit it.", Snackbar.LENGTH_LONG).show()
                }
            }
            true
        }
        R.id.action_search -> {
            Log.d(myTag, "Action Search")

            true
        }

        else -> super.onOptionsItemSelected(item)
    }



}


