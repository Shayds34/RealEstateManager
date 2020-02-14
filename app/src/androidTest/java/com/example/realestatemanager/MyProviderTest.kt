package com.example.realestatemanager

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.example.realestatemanager.models.Property
import com.example.realestatemanager.utils.MyContentProvider
import com.example.realestatemanager.utils.RealEstateDBHelper
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

        // val cursor2 = appContext.contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        val cursor = appContext.contentResolver.query(Uri.parse(queryUri), null, selectionClause, selectionArgs, sortOrder)
        Log.d("SplashActivity", "Is cursor null ? $cursor")

        if (cursor != null) {

            if (cursor.count > 0){
                while (cursor.moveToNext()) {
                    val property = Property(
                        cursor.getInt(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_PROPERTY_ID)),
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_TYPE)),
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_NEIGHBORHOOD)),
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_PRICE)),
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_SIZE)),
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_ROOMS)),
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_BATHROOMS)),
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_BEDROOMS)),
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_ADDRESS)),
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_ZIP_CODE)),
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_CITY)),
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_COUNTRY)),
                        ArrayList(),
                        "",
                        true,
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_CREATION_DATE)),
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_SELLING_DATE)),
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_AUTHOR))
                    )
                    Log.d("SplashActivity", "Current Property is $property")
                }
            } else {
                Log.d("SplashActivity", "No data returned.")
            }

            cursor.close()
        } else {
            Log.d("SplashActivity", "Cursor is null.")
        }

    }
}