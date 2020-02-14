package com.example.realestatemanager

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.example.realestatemanager.models.Property
import com.example.realestatemanager.utils.MyContentProvider
import org.junit.Before
import org.junit.Test

class MyProviderTest {

    private lateinit var appContext : Context
    private lateinit var mListOfProperties : ArrayList<Property>

    @Before
    fun setUp() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        mListOfProperties = ArrayList<Property>()
    }

    @Test
    fun getData() {
        val queryUri = MyContentProvider.CONTENT_URI.toString()
        val projection =  arrayOf(MyContentProvider.CONTENT_PATH)
        val selectionClause : String?
        val selectionArgs: ArrayList<Property>?
        val sortOrder: String? = null

        // Display ALL
        selectionClause = null
        selectionArgs = null

        val cursor = appContext.contentResolver.query(Uri.parse(queryUri), null, selectionClause, selectionArgs, sortOrder)
        Log.d("SplashActivity", "Is cursor null ? $cursor")

        if (cursor != null) {

            if (cursor.count > 0){
                cursor.moveToFirst()
                val columnIndex = cursor.getColumnIndex(projection[0])

                //region {log}
                Log.d("SplashActivity", "Cursor is ${cursor.count}")
                Log.d("SplashActivity", "columnIndex is $columnIndex")
                //endregion

                do {
                    Log.d("SplashActivity", "columnIndex in do is $columnIndex")
                    //    val property = cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_TYPE))
                    val property = cursor.getString(columnIndex)

                    val id = cursor.getString(0)

//                    val type = cursor.getString(cursor.getColumnIndexOrThrow(RealEstateDBHelper.COLUMN_TYPE))

                    Log.d("SplashActivity", "Current Property is $property and id is $id and type is ")
                } while (cursor.moveToNext())

            } else {
                Log.d("SplashActivity", "No data returned.")
            }

            cursor.close()
        } else {
            Log.d("SplashActivity", "Cursor is null.")
        }

    }
}