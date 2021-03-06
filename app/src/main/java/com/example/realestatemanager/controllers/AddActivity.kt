package com.example.realestatemanager.controllers

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.example.realestatemanager.R
import com.example.realestatemanager.models.Property
import com.example.realestatemanager.utils.MyPhotosAdapter
import com.example.realestatemanager.utils.RealEstateDBHelper
import com.example.realestatemanager.utils.Utils
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.toolbar.*
import java.io.FileNotFoundException


class AddActivity : AppCompatActivity(){


    companion object {
        private const val TAG = "AddActivity"

    }

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

    private lateinit var photosList : ArrayList<String>
    private lateinit var adapter: MyPhotosAdapter
    private lateinit var sharedPreferences: SharedPreferences
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val propertyType = arrayOf(
            getString(R.string.house),
            getString(R.string.apartment),
            getString(R.string.penthouse),
            getString(R.string.loft),
            getString(R.string.cottage)
        )

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.dropdown_menu_popup_item,
            propertyType
        )

        sharedPreferences = getSharedPreferences("com.example.realestatemanager", Context.MODE_PRIVATE)

        tv_type.setAdapter(adapter)

        //#region {Toolbar}
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        //endregion

        photosList = ArrayList()
        configureRecyclerView()
        author = sharedPreferences.getString("CurrentUserFirstName", "") + " " + sharedPreferences.getString("CurrentUserLastName", "")

        tv_price.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_euro_symbol_black_24dp, 0, 0, 0) // TODO : SharedPreferences Currency

        val metricSystem = "m²" // TODO : SharedPreferences MetricSystem
        tv_surface_layout.hint = "Surface in $metricSystem"

        //#region {Add Image Button}
        add_image_button.setOnClickListener{
            Log.d(TAG, "Add Image Button clicked.")

            val photoPicker = Intent()
            photoPicker.type = "image/*"
            photoPicker.action = Intent.ACTION_OPEN_DOCUMENT
            startActivityForResult(photoPicker, 1)
        }
        //endregion

        // region {Add Button}
        // Add a new property entered by user to the SQLite Database.
        add_button.setOnClickListener{
            Log.d(TAG, "Add Button clicked.")

            // TODO var pointOfInterest: String
            // TODO var status: Boolean
            val creationDate = Utils.getTodayDate().toString()
            // TODO var sellingDate

            if (isValidForm()) {
                Log.d(TAG, "Validate Form, adding property to DB.")
                val property = Property(
                    0, // This will not be added to the DB. It will take the real id from DB later.
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
                    photosList,
                    "",
                    "",
                    creationDate,
                    "",
                    author)

                val dbHelper = RealEstateDBHelper(this, null)
                dbHelper.addProperty(property)
                dbHelper.close()
                finish()
            } else {
                Toast.makeText(this, "You should enter all text and at least one picture before adding a property to the Database.", Toast.LENGTH_LONG).show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            try {
                val imageUri : Uri? = data?.data

                if (!photosList.contains(imageUri.toString())){
                    photosList.add(imageUri.toString())
                    empty_recycler_view.visibility = View.GONE
                } else {
                    Toast.makeText(this, "This picture is already in the list.", Toast.LENGTH_LONG).show()
                }
                adapter.notifyDataSetChanged()

                Log.d(TAG, "Image is: $imageUri")
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, "Something went wrong.", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "You haven't picked an Image yet.", Toast.LENGTH_LONG).show()
        }
    }

    //#region {Configure Photos Gallery}
    private fun configureRecyclerView() {
        Log.d(TAG, "configureRecyclerView")

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        adapter = MyPhotosAdapter(this, photosList)
        adapter.notifyDataSetChanged()
        recycler_view.adapter = adapter
        recycler_view.layoutManager = layoutManager
    }
    //endregion

    // region {Alert Dialog for Cancel Button}
    private fun showCancelAlertDialog(activity: Activity) {
        AlertDialog.Builder(activity).apply {
            setTitle("Be careful.")
            setMessage("You will delete all the information you've just entered. Are you sure you want to cancel ?")
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
    private fun isValidForm() : Boolean {
        var valid = true

        if (photosList.size == 0) {
            Toast.makeText(this, "You must add at least one picture.", Toast.LENGTH_LONG).show()
            valid = false
        }

        TransitionManager.beginDelayedTransition(container)

        description = tv_description.text.toString()
        if (description.isEmpty()) {
            tv_description.error = "Required"
            valid = false
        }

        // Test EditText
        type = tv_type.text.toString().trim()
        if (type.isEmpty()){
            tv_type.error = "Required"
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

        address = tv_address_street.text.toString()
        if (address.isEmpty()) {
            tv_address_street.error = "Required"
            valid = false
        }

        neighborhood = tv_neighborhood.text.toString()

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

        return valid
    }
    // endregion

    //#region {Update Photos}
    fun deletePhoto(photo: String){
        this.photosList.remove(photo)
        if (photosList.size == 0){
            empty_recycler_view.visibility = View.VISIBLE
        }
    }
    //endregion

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showCancelAlertDialog(this)
    }
}