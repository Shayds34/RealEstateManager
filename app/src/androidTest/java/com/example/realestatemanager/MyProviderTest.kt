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

    @Before
    fun setUp() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
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

        val cursor = appContext.contentResolver.query(Uri.parse(queryUri), projection, selectionClause, selectionArgs, sortOrder)
        Log.d("MyProviderTest", "Is cursor null ? $cursor")

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
                        ArrayList(), // TODO PHOTOS CURSOR FROM CONTENT PROVIDER
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_POINT_OF_INTEREST)),
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_STATUS)),
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_CREATION_DATE)),
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_SELLING_DATE)),
                        cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_AUTHOR))
                    )
                    Log.d("MyProviderTest", "Current Property is $property")
                }
            } else {
                Log.d("MyProviderTest", "No data returned.")
            }

            cursor.close()
        } else {
            Log.d("MyProviderTest", "Cursor is null.")
        }

    }
}