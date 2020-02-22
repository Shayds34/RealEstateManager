package com.example.realestatemanager.controllers

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.R
import com.example.realestatemanager.utils.RealEstateDBHelper
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar.*


class SearchActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val TAG = "SearchActivity"
    }

    private lateinit var chipsType : ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        //#region {General Settings}
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        toolbar.setTitle(R.string.app_name)

        button_search.setOnClickListener(this)

        chipsType = ArrayList()

        for (i in 0 until type_chips.childCount) {
            val chip = type_chips.getChildAt(i) as Chip
            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    chipsType.add(buttonView.text.toString())
                } else {
                    chipsType.remove(buttonView.text.toString())
                }
                if (chipsType.isNotEmpty()) {
                    Toast.makeText(
                        this,
                        chipsType.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        //endregion
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onClick(v : View) {
        when (v.id) {
            R.id.button_search -> {
                // TODO replace with the correct query
                // TODO SQL SEARCH QUERY
                // @Query("SELECT * FROM $PROPERTY_TABLE_NAME " +
                //"INNER JOIN $AMENITY_TABLE_NAME ON $AMENITY_TABLE_NAME.property = $PROPERTY_TABLE_NAME.property_id " +
                //"INNER JOIN $ADDRESS_TABLE_NAME ON $ADDRESS_TABLE_NAME.address_id = $PROPERTY_TABLE_NAME.property_id WHERE " +
                //"$AMENITY_TABLE_NAME.type_amenity IN (:listAmenities) " +
                //"AND ($ADDRESS_TABLE_NAME.neighbourhood LIKE :neighborhood) " +
                //"AND ($PROPERTY_TABLE_NAME.price BETWEEN :minPrice [... continuing ...]

                //region {Prices}
                val minPrice : Int = if (Integer.parseInt(tv_price_min.text.toString()) > 0) {
                    Integer.parseInt(tv_price_min.text.toString())
                } else {
                    0
                }

                val maxPrice : Int = if (Integer.parseInt(tv_price_max.text.toString()) > 0) {
                    Integer.parseInt(tv_price_max.text.toString())
                } else {
                    0
                }
                //endregion

                //region {Types}

                //endregion

                val data = RealEstateDBHelper(this, null).getListOfSearchedProperties(minPrice, maxPrice, chipsType)

                if (data.size > 0) {
                    intent = Intent(this, SearchedPropertiesActivity::class.java)
                    intent.putParcelableArrayListExtra("properties", data)
                    startActivity(intent)
                } else {
                    Snackbar.make(linearLayout, "There is no result for your current search terms.", Snackbar.LENGTH_INDEFINITE).setAction("DISMISS"){}.show()
                }
            }
        }
    }
}
