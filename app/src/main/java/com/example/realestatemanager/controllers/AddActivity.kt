package com.example.realestatemanager.controllers

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.example.realestatemanager.R
import com.example.realestatemanager.models.Property
import com.example.realestatemanager.utils.MyPhotosAdapter
import com.example.realestatemanager.utils.RealEstateDBHelper
import com.example.realestatemanager.utils.Utils
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add.*
import java.io.FileNotFoundException

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

    private lateinit var photosList : ArrayList<String>
    private lateinit var adapter: MyPhotosAdapter
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        //#region {Toolbar}
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        //endregion

        photosList = ArrayList()
        configureRecyclerView()

        //#region {Add Image Button}
        add_image_button.setOnClickListener{
            Log.d(myTag, "Add Image Button clicked.")

            val photoPicker = Intent()
            photoPicker.type = "image/*"
            photoPicker.action = Intent.ACTION_PICK
            startActivityForResult(photoPicker, 1)
        }
        //endregion

        // region {Add Button}
        // Add a new property entered by user to the SQLite Database.
        add_button.setOnClickListener{
            Log.d(myTag, "Add Button clicked.")

            // TODO var pointOfInterest: String
            // TODO var status: Boolean
            val creationDate = Utils.getTodayDate().toString()
            // TODO var sellingDate

            if (isValidForm()) {
                Log.d(myTag, "Validate Form, adding property to DB.")
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
                    true,
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
                } else {
                    Toast.makeText(this, "This picture is already in the list.", Toast.LENGTH_LONG).show()
                }
                adapter.notifyDataSetChanged()

                Log.d(myTag, "Image is: $imageUri")
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
        Log.d(myTag, "configureRecyclerView")

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
            setMessage("You will erase all the information you just entered. Are you sure to cancel ?")
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
            tv_description_layout.isErrorEnabled = true
            tv_description_layout.error = "Required"
            valid = false
        } else {
            tv_description_layout.isErrorEnabled = false
        }

        // Test EditText
        type = tv_type.text.toString().trim()
        if (type.isEmpty()){
            tv_type_layout.isErrorEnabled = true
            tv_type_layout.error = "Required"
            valid = false
        } else {
            tv_type_layout.isErrorEnabled = false
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

    //#region {Update Photos}
    fun deletePhoto(photo: String){
        this.photosList.remove(photo)
    }
    //endregion

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        R.id.action_add -> {
            Log.d(myTag, "Action Add")
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
            finish()
            true
        }
        R.id.action_edit -> {
            Log.d(myTag, "Action Edit")
            Snackbar.make(container, "You have to select a property to edit it.", Snackbar.LENGTH_LONG).show()
            true
        }
        R.id.action_search -> {
            Log.d(myTag, "Action Search")
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}