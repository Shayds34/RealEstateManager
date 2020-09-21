package com.example.realestatemanager.controllers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.realestatemanager.R
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.toolbar.*
import java.io.FileNotFoundException

class UserActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "UserActivity"
    }

    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        //#region {General Settings}
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        //endregion

        //#region {Toolbar}
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        //endregion

        //#region {Profile Picture}
        profile_picture.setOnClickListener{
            Log.d(TAG, "Add Image Button clicked.")

            val photoPicker = Intent()
            photoPicker.action = Intent.ACTION_PICK
            photoPicker.type = "image/*"
            startActivityForResult(photoPicker, 1)
        }
        //endregion

        //#region {SharedPreferences}
        sharedPreferences = getSharedPreferences("com.example.realestatemanager", Context.MODE_PRIVATE)

        // TODO First name & last name "getters & setters"

        // True is Dollars - False is Euros
        if (sharedPreferences.getBoolean("CurrentCurrency", false)) {
            currency_toggle_group.check(R.id.button_currency_2)
        } else {
            currency_toggle_group.check(R.id.button_currency_1)
        }

        // True is sq ft - False is m²
        if (sharedPreferences.getBoolean("CurrentMetric", false)) {
            metric_toggle_group.check(R.id.button_metric_2)
        } else {
            metric_toggle_group.check(R.id.button_metric_1)
        }

        Glide.with(this)
            .load(sharedPreferences.getString("CurrentPicture", "https://nsa40.casimages.com/img/2020/02/13/mini_200213095727630860.jpg"))
            .circleCrop()
            .into(profile_picture)
        //endregion

        //#region {Toggle Buttons}
        currency_toggle_group.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked){
                when (checkedId) {
                    R.id.button_currency_1 -> {
                        Toast.makeText(this, "Your current currency changed to Euros", Toast.LENGTH_LONG).show()
                        sharedPreferences.edit().putBoolean("CurrentCurrency", false).apply()
                    }
                    R.id.button_currency_2 -> {
                        Toast.makeText(this, "Your current currency changed to Dollars.", Toast.LENGTH_LONG).show()
                        sharedPreferences.edit().putBoolean("CurrentCurrency", true).apply()
                    }
                }
            }
        }

        metric_toggle_group.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked){
                when (checkedId) {
                    R.id.button_metric_1 -> {
                        Toast.makeText(this, "Your current metric system changed to m².", Toast.LENGTH_LONG).show()
                        sharedPreferences.edit().putBoolean("CurrentMetric", false).apply()
                    }
                    R.id.button_metric_2 -> {
                        Toast.makeText(this, "Your current metric system changed to sq ft.", Toast.LENGTH_LONG).show()
                        sharedPreferences.edit().putBoolean("CurrentMetric", true).apply()
                    }
                }
            }
        }
        //endregion
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            try {
                val imageUri : Uri? = data?.data

                val sharedPreferences = getSharedPreferences("com.example.realestatemanager", Context.MODE_PRIVATE)
                sharedPreferences.edit().putString("CurrentPicture", imageUri.toString()).apply()

                //region {Glide}
                Glide.with(this)
                    .load(imageUri.toString())
                    .circleCrop()
                    .into(profile_picture)
                //endregion

                Log.d(TAG, "Image is: $imageUri")
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, "Something went wrong.", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "You haven't picked an image yet.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            updateSharedPreferences()
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.updateSharedPreferences()
        Toast.makeText(this, sharedPreferences.getString("CurrentUserFirstName", "") + " " + sharedPreferences.getString("CurrentUserLastName", ""), Toast.LENGTH_LONG).show()
    }

    private fun updateSharedPreferences(){
        if (register_form_first_name.text.isNullOrEmpty())
        {
            sharedPreferences.edit().putString("CurrentUserFirstName", "").apply()
        }
        else
        {
            sharedPreferences.edit().putString("CurrentUserFirstName", register_form_first_name.text.toString()).apply()
        }


        if (register_form_last_name.text.isNullOrEmpty())
        {
            sharedPreferences.edit().putString("CurrentUserLastName", "").apply()
        }
        else
        {
            sharedPreferences.edit().putString("CurrentUserLastName", register_form_last_name.text.toString()).apply()
        }
    }
}
