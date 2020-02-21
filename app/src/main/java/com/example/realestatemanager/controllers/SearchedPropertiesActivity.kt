package com.example.realestatemanager.controllers

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.realestatemanager.R
import com.example.realestatemanager.models.Property
import com.example.realestatemanager.utils.Communicator
import com.example.realestatemanager.utils.PropertiesAdapter
import com.example.realestatemanager.utils.RealEstateDBHelper
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.toolbar.*

class SearchedPropertiesActivity : AppCompatActivity(), Communicator {

    companion object {
        const val TAG = "SearchedProperties"
    }

    //#region {Initialization}
    private lateinit var adapter: PropertiesAdapter
    private lateinit var data : ArrayList<Property>
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_searched_list)

        //#region {General Settings}
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        //endregion

        data = intent.getParcelableArrayListExtra("properties")
        configureRecyclerView()
    }

    private fun configureRecyclerView() {
        Log.d(TAG, "configureRecyclerView")

        // Fetch our data.
        // TODO replace this by previous query done by the SearchActivity
//        val db = RealEstateDBHelper(this, null)
//        data = db.getListOfProperties()

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recycler_view.layoutManager = LinearLayoutManager(this)
        } else {
            recycler_view.layoutManager = GridLayoutManager(this, 2)
        }

        adapter = PropertiesAdapter(this, data)
        adapter.notifyDataSetChanged()
        recycler_view.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun displayDetailsOfGood(property: Property) {
        // Check if app is Portrait or Landscape according to isDualPane Boolean
        val intent = Intent(this, PropertyActivity::class.java)
        intent.putExtra("property", property)
        startActivity(intent)

    }
}