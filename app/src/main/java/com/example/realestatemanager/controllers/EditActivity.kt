package com.example.realestatemanager.controllers

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.realestatemanager.R
import com.example.realestatemanager.models.Property
import com.example.realestatemanager.utils.MyPhotosAdapter
import com.example.realestatemanager.utils.RealEstateDBHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_edit.*
import java.io.FileNotFoundException

class EditActivity : AppCompatActivity() {

    //#region {Initialization}
    private var myTag = "EditActivity"
    private lateinit var adapter: MyPhotosAdapter
    private lateinit var property: Property

    private lateinit var photosList : ArrayList<String>
    private lateinit var photosToBeAdded : ArrayList<String>
    private lateinit var photosToBeDeleted: ArrayList<String>
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        photosToBeAdded = ArrayList()
        photosToBeDeleted = ArrayList()

        //#region {Create Property Edition}
        property = intent.getParcelableExtra("property")
        displayDetailsOfGood(property)
        configureRecyclerView(property)
        //endregion

        //#region {Edit Button}
        edit_button.setOnClickListener {
            val dbHelper = RealEstateDBHelper(this, null)
            dbHelper.writableDatabase

            //#region {Get information from EditTexts}
            if (tv_description.text.isNotEmpty()){
                property.description = tv_description.text.toString()
            }

            if (tv_type.text.isNotEmpty()) {
                property.type = tv_type.text.toString()
            }

            if (tv_surface.text.isNotEmpty()){
                property.size = tv_surface.text.toString()
            }

            if (tv_room.text.isNotEmpty()){
                property.rooms = tv_room.text.toString()
            }

            if (tv_price.text.isNotEmpty()) {
                property.price = tv_price.text.toString()
            }

            if (tv_bathroom.text.isNotEmpty()){
                property.bathrooms = tv_bathroom.text.toString()
            }

            if (tv_bedroom.text.isNotEmpty()){
                property.bedrooms = tv_bedroom.text.toString()
            }

            if (tv_address_street.text.isNotEmpty()){
                property.address = tv_address_street.text.toString()
            }

            if (tv_neighborhood.text.isNotEmpty()) {
                property.neighborhood = tv_neighborhood.text.toString()
            }

            if (tv_address_city.text.isNotEmpty()){
                property.city = tv_address_city.text.toString()
            }

            if (tv_address_zip_code.text.isNotEmpty()){
                property.zip_code = tv_address_zip_code.text.toString()
            }

            if (tv_address_country.text.isNotEmpty()){
                property.country = tv_address_country.text.toString()
            }

            if (tv_author.text.isNotEmpty()){
                property.author = tv_author.text.toString()
            }
            //endregion

            // TODO tri des 3 listes
            dbHelper.addNewPhotos(photosToBeAdded, property)
            if (photosToBeDeleted.isNotEmpty()){
                dbHelper.deletePhotos(photosToBeDeleted, property)
            }
            dbHelper.editProperty(property)
            dbHelper.close()
            finish()
        }
        //endregion

        //#region {Add Image Button}
        photosList = ArrayList()

        add_image_button.setOnClickListener{
            Log.d(myTag, "Add Image Button clicked.")

            val photoPicker = Intent()
            photoPicker.type = "image/*"
            photoPicker.action = Intent.ACTION_PICK
            startActivityForResult(photoPicker, 1)
        }
        //endregion

        // region {Cancel Button}
        // Cancel the form to edit the property.
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

                if (!property.photos.contains(imageUri.toString())){
                    photosToBeAdded.add(imageUri.toString())
                    property.photos.add(imageUri.toString())
                    adapter.notifyDataSetChanged()
                    Log.d(myTag, "Image is: $imageUri")
                } else {
                    Snackbar.make(container, "This picture has been added already.", Snackbar.LENGTH_LONG).show()
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, "Something went wrong.", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "You haven't picked an Image yet.", Toast.LENGTH_LONG).show()
        }
    }

    //#region {Configure Photos Gallery}
    private fun configureRecyclerView(property: Property) {
        Log.d(myTag, "configureRecyclerView")

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        adapter = MyPhotosAdapter(this, property)
        adapter.notifyDataSetChanged()
        recycler_view.adapter = adapter
        recycler_view.layoutManager = layoutManager
    }
    //endregion

    //#region {Display Information}
    private fun displayDetailsOfGood(property : Property){
        tv_description.hint = property.description
        tv_type.hint = property.type
        tv_price.hint = property.price
        tv_surface.hint = property.size
        tv_room.hint = property.rooms
        tv_bathroom.hint = property.bathrooms
        tv_bedroom.hint = property.bedrooms
        tv_address_street.hint = property.address
        tv_neighborhood.hint = property.neighborhood
        tv_address_zip_code.hint = property.zip_code
        tv_address_city.hint = property.city
        tv_address_country.hint = property.country
        tv_author.hint = property.author
    }
    //endregion

    //#region {Update Property}
    fun updateProperty(photos: ArrayList<String>){
        this.photosToBeDeleted = photos
    }
    //endregion

    // region {Alert Dialog for Cancel Button}
    private fun showCancelAlertDialog(activity: Activity) {
        AlertDialog.Builder(activity).apply {
            setTitle("Be careful.")
            setMessage("You will erase all the information you just entered. Are you sure you want to cancel ?")
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
}

