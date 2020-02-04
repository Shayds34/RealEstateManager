package com.example.realestatemanager.controllers

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO reverse
//        setContentView(R.layout.activity_splash_screen)
        setContentView(R.layout.activity_login)

        // Redirect from this to MainActivity after 2 secs.
        Handler().postDelayed(
            {
                val intent = Intent (this , MainActivity::class.java)
                startActivity(intent)
                finish()
            },
            DELAY_IN_MILLIS.toLong())
    }

    companion object {
        private const val DELAY_IN_MILLIS = 10000
    }
}