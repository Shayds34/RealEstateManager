package com.example.realestatemanager.controllers

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.R
import com.example.realestatemanager.models.Property
import com.example.realestatemanager.utils.RealEstateDBHelper
import com.example.realestatemanager.utils.Utils
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity(){
    private val myTag = "AddActivity"

    // region {Initialization}
    private lateinit var type: String
    private lateinit var neighborhood : String
    private lateinit var price : String
    private lateinit var size : String
    private lateinit var rooms : String
    private lateinit var bathrooms : String
    private lateinit var bedrooms : String
    private lateinit var description : String
    private lateinit var address : String
    private lateinit var zipCode : String
    private lateinit var city : String
    private lateinit var country : String
    private lateinit var author : String
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        // region {Add Button}
        // Add a new property entered by user to the SQLite Database.
        add_button.setOnClickListener{
            Log.d(myTag, "Add Button clicked.")

            // TODO var photos: List<String>
            // TODO var pointOfInterest: String
            // TODO var status: Boolean
            val creationDate = Utils.getTodayDate().toString()
            // TODO var sellingDate

            if (validateForm()) {
                Log.d(myTag, "Validate Form, adding property to DB.")
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
                finish()
            } else {
                Toast.makeText(this, "You should enter all text before adding a property to the Database.", Toast.LENGTH_LONG).show()
            }
        }
        // endregion

        // region {Cancel Button}
        // Cancel the form to add a new property.
        cancel_button.setOnClickListener {
            showCancelAlertDialog(this)
        }
        // endregion

    }

    // region {Alert Dialog for Cancel Button}
    private fun showCancelAlertDialog(activity: Activity) {
        AlertDialog.Builder(activity).apply {
            setTitle("Be careful.")
            setMessage("You will cancel all the information you just entered. Are you sure to cancel ?")
            // User confirms he wants to cancel.
            setPositiveButton("Yes") { _, _ ->
                // Closing this activity.
                finish()
            }
            // User doesn't want to cancel anymore.
            setNegativeButton("No") { _, _ ->
                // Closing Alert Dialog.
            }
        }.create().show()
    }
    // endregion

    // region {EditText Validation}
    private fun validateForm() : Boolean {
        var valid = true

        // Test EditText

        type = tv_type.text.toString()
        if (type.isEmpty()){
            tv_type.error = "Required"
            valid = false
        }

        neighborhood = tv_neighborhood.text.toString()
        if (neighborhood.isEmpty()) {
            tv_neighborhood.error = "Required"
            valid = false
        }

        price = tv_price.text.toString()
        if (price.isEmpty()) {
            tv_price.error = "Required"
            valid = false
        }

        size = tv_surface.text.toString()
        if (size.isEmpty()) {
            tv_surface.error = "Required"
            valid = false
        }

        rooms = tv_room.text.toString()
        if (rooms.isEmpty()) {
            tv_room.error = "Required"
            valid = false
        }

        bathrooms = tv_bathroom.text.toString()
        if (bathrooms.isEmpty()) {
            tv_bathroom.error = "Required"
            valid = false
        }

        bedrooms = tv_bedroom.text.toString()
        if (bedrooms.isEmpty()) {
            tv_bedroom.error = "Required"
            valid = false
        }

        description = tv_description.text.toString()
        if (description.isEmpty()) {
            tv_description.error = "Required"
            valid = false
        }

        address = tv_address_street.text.toString()
        if (address.isEmpty()) {
            tv_address_street.error = "Required"
            valid = false
        }

        zipCode = tv_address_zip_code.text.toString()
        if (zipCode.isEmpty()) {
            tv_address_zip_code.error = "Required"
            valid = false
        }

        city = tv_address_city.text.toString()
        if (city.isEmpty()) {
            tv_address_city.error = "Required"
            valid = false
        }

        country = tv_address_country.text.toString()
        if (country.isEmpty()) {
            tv_address_country.error = "Required"
            valid = false
        }

        author = tv_author.text.toString()
        if (author.isEmpty()) {
            tv_author.error = "Required"
            valid = false
        }

        return valid
    }
    // endregion

}