package com.example.realestatemanager.controllers

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.realestatemanager.R
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.toolbar.*

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        //#region {Toolbar}
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        //endregion




        //region {Glide}
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .circleCrop()

        Glide.with(this)
            .applyDefaultRequestOptions(requestOptions)
            .load("https://nsa40.casimages.com/img/2020/02/13/mini_200213095727630860.jpg")
            .into(profile_picture)
        //endregion



    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
