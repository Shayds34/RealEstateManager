package com.example.realestatemanager.controllers

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.realestatemanager.R
import com.example.realestatemanager.models.Property
import com.example.realestatemanager.ui.property.PropertyFragment
import com.example.realestatemanager.utils.Communicator
import com.example.realestatemanager.utils.Utils
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), Communicator, NavigationView.OnNavigationItemSelectedListener {

    companion object {
        private const val TAG = "MainActivity"
        private const val REQUEST_READ_EXTERNAL_STORAGE = 10
    }

    //#region {Initialization}
    private var isDualPane = false

    private lateinit var drawer : DrawerLayout
    private lateinit var toggle : ActionBarDrawerToggle

    private lateinit var mProperty: Property
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("com.example.realestatemanager", Context.MODE_PRIVATE)

        val headerView = nav_view.getHeaderView(0)

        Glide.with(this)
            .load(sharedPreferences.getString("CurrentPicture", "https://nsa40.casimages.com/img/2020/02/13/mini_200213095727630860.jpg"))
            .circleCrop()
            .into(headerView.findViewById(R.id.profile_picture))

        //#region {Toolbar}
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        toolbar.setTitle(R.string.app_name)

        if (collapsing_toolbar != null) {
            collapsing_toolbar!!.title = this.resources.getString(R.string.app_name)
            collapsing_toolbar!!.setExpandedTitleColor(ContextCompat.getColor(this, R.color.transparent))
            collapsing_toolbar!!.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite))

            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)

            Glide.with(this)
                .load("https://images.fineartamerica.com/images-medium-large/1-new-york-skyline-mircea-costina-photography.jpg")
                .centerCrop()
                .into(image)

            val height = displayMetrics.heightPixels

            collapsing_toolbar!!.layoutParams.height = height / 3
        }
        //endregion

        //#region {NavigationDrawer}
        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        val navigationView : NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        //endregion

        // Check Storage Permission
        checkPermission()

        //#region {Portrait/Landscape Verification}
        val propertyFragmentView = findViewById<View>(R.id.fragmentB)
        isDualPane = propertyFragmentView?.visibility == View.VISIBLE
        //endregion
    }


    //#region {Storage Permission}
    private fun checkPermission(){
        if  (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Log.d(TAG, "Should Show Request Permission")
                } else {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_EXTERNAL_STORAGE)
                }
            }
    }
    //endregion

    override fun displayDetailsOfGood(property: Property) {
        mProperty = property

        // Check if app is Portrait or Landscape according to isDualPane Boolean
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

    //#region {Toolbar Menu Items}
    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.action_add -> {
            Log.d(TAG, "Action Add")
            val intent = Intent(this, AddActivity::class.java)
            if(isDualPane){
                startActivity(intent)
            } else {
                startActivity(intent)
            }
            true
        }
        R.id.action_edit -> {
            Log.d(TAG, "Action Edit")
            // Check if user is in portrait/landscape version
            // Check if user has chosen an item from list
            if (isDualPane){
                // Landscape
                Log.d(TAG, "Landscape.")
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("property", mProperty)
                startActivity(intent)
            } else {
                // Portrait
                Log.d(TAG, "Portrait.")
                if (fragment_container != null) {
                    Snackbar.make(fragment_container, "You have to select a property to edit it.", Snackbar.LENGTH_LONG).show()
                }
            }
            true
        }
        R.id.action_search -> {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
            true
        }

        else -> super.onOptionsItemSelected(item)
    }
    //endregion

    //#region {Navigation Drawer Items}
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_map -> {
                if (Utils().locationServicesEnabled(this)) {
                    val intent = Intent(applicationContext, MapsActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "You have to enable Location Services to access the map.", Toast.LENGTH_LONG).show()
                }
            }
            R.id.nav_add_property -> {
                val intent = Intent(this, AddActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_simulator -> {
                val intent = Intent(this, CalculatorActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_user -> {
                val intent = Intent(this, UserActivity::class.java)
                startActivity(intent)
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
    //endregion

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }
}


