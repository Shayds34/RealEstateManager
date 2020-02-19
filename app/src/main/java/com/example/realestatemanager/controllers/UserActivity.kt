package com.example.realestatemanager.controllers

import android.app.Activity
import android.content.Context
import android.content.Intent
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        //#region {Toolbar}
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        //endregion

        //#region {Profile Picture}
        profile_picture.setOnClickListener{
            Log.d(TAG, "Add Image Button clicked.")

            val photoPicker = Intent()
            photoPicker.type = "image/*"
            photoPicker.action = Intent.ACTION_PICK
            startActivityForResult(photoPicker, 1)
        }
        //endregion

        //#region {SharedPreferences}
        val sharedPreferences = getSharedPreferences("com.example.realestatemanager", Context.MODE_PRIVATE)
        currency_switch.isChecked = sharedPreferences.getBoolean("CurrentCurrency", false)
        metric_switch.isChecked = sharedPreferences.getBoolean("CurrentMetric", false)

        Glide.with(this)
            .load(sharedPreferences.getString("CurrentPicture", "https://nsa40.casimages.com/img/2020/02/13/mini_200213095727630860.jpg"))
            .circleCrop()
            .into(profile_picture)
        //endregion

        //#region {Switches}
        currency_switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Your current currency changed to Dollars.", Toast.LENGTH_LONG).show()
                sharedPreferences.edit().putBoolean("CurrentCurrency", true).apply()
            } else {
                Toast.makeText(this, "Your current currency changed to Euros", Toast.LENGTH_LONG).show()
                sharedPreferences.edit().putBoolean("CurrentCurrency", false).apply()
            }
        }

        metric_switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Your current metric system changed to sq ft.", Toast.LENGTH_LONG).show()
                sharedPreferences.edit().putBoolean("CurrentMetric", true).apply()
            } else {
                Toast.makeText(this, "Your current metric system changed to m².", Toast.LENGTH_LONG).show()
                sharedPreferences.edit().putBoolean("CurrentMetric", false).apply()
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
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    companion object {
        private const val TAG = "UserActivity"
    }
}
