package com.example.realestatemanager

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.example.realestatemanager.models.Property
import com.example.realestatemanager.utils.MyContentProvider
import com.example.realestatemanager.utils.RealEstateDBHelper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MyProviderTest {

    private lateinit var appContext : Context

    @Before
    fun setUp() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun getData_shouldReturnMultiplePropertyObject() {
        val propertiesUri = MyContentProvider.PROPERTIES_URI.toString()
        val photosUri = MyContentProvider.PHOTOS_URI.toString()
        val selectionClause : String?
        val selectionArgs: ArrayList<Property>?
        val sortOrder: String? = null

        val listOfPropertyToTest = ArrayList<Property>()

        var id: Int
        var type: String
        var neighborhood: String
        var price : String
        var size : String
        var rooms : String
        var bathrooms : String
        var bedrooms : String
        var description : String
        var address : String
        var zipCode : String
        var city : String
        var country : String
        var photos : ArrayList<String>
        var pointOfInterest : String
        var status : String
        var creationDate : String
        var sellingDate : String
        var author : String

        // Display ALL
        selectionClause = null
        selectionArgs = null

        val cursor = appContext.contentResolver.query(Uri.parse(propertiesUri), arrayOf(MyContentProvider.PROPERTIES_PATH), selectionClause, selectionArgs, sortOrder)
        Log.d("MyProviderTest", "Is cursor null ? $cursor")

        if (cursor != null) {

            if (cursor.count > 0){
                while (cursor.moveToNext()) {

                    id = cursor.getInt(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_PROPERTY_ID))
                    type = cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_TYPE))
                    neighborhood = cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_NEIGHBORHOOD))
                    price = cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_PRICE))
                    size = cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_SIZE))
                    rooms = cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_ROOMS))
                    bathrooms = cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_BATHROOMS))
                    bedrooms = cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_BEDROOMS))
                    description = cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_DESCRIPTION))
                    address = cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_ADDRESS))
                    zipCode = cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_ZIP_CODE))
                    city = cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_CITY))
                    country = cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_COUNTRY))
                    photos = ArrayList()

                    // Create list of photos from photos table from Database
                    val photoCursor: Cursor? = appContext.contentResolver.query(Uri.parse(photosUri), arrayOf(MyContentProvider.PHOTOS_PATH), selectionClause, selectionArgs, sortOrder)
                        if (photoCursor!!.moveToFirst()){
                        while (!photoCursor.isAfterLast) {
                            Log.d("MyProviderTest", "Photo Cursor")
                            val propertyId = photoCursor.getInt(photoCursor.getColumnIndexOrThrow(RealEstateDBHelper.COLUMN_FK_ID_PROPERTY))
                                if (propertyId == id){
                                    photos.add(photoCursor.getString(photoCursor.getColumnIndex(RealEstateDBHelper.COLUMN_URI_PHOTOS)))
                                }
                            photoCursor.moveToNext()
                        }
                    }
                    photoCursor.close()

                    pointOfInterest = cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_POINT_OF_INTEREST))
                    status = cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_STATUS))
                    creationDate = cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_CREATION_DATE))
                    sellingDate = cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_SELLING_DATE))
                    author = cursor.getString(cursor.getColumnIndex(RealEstateDBHelper.COLUMN_AUTHOR))


                    val property = Property(
                        id,
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
                        photos,
                        pointOfInterest,
                        status,
                        creationDate,
                        sellingDate,
                        author
                    )
                    listOfPropertyToTest.add(property)
                    Log.d("MyProviderTest", "Current Property is $property")
                }
            } else {
                Log.d("MyProviderTest", "No data returned.")
            }

            cursor.close()
        } else {
            Log.d("MyProviderTest", "Cursor is null.")
        }

        val db = RealEstateDBHelper(appContext, null).getListOfProperties()

        assertEquals(listOfPropertyToTest.size, db.size)
        assertEquals(listOfPropertyToTest[0].type, db[0].type)

        Log.d("MyProviderTest", "${listOfPropertyToTest[4].photos[2]}, ${db[4].photos[2]}")
        assertEquals(listOfPropertyToTest[4].photos[2], db[4].photos[2])

        for (i in 0 until listOfPropertyToTest.size) {
            Log.d("MyProviderTest", "for list ${listOfPropertyToTest[i].type} and db ${db[i].type}")
            assertEquals(listOfPropertyToTest[i].type, db[i].type)
            for (p in 0 until listOfPropertyToTest[i].photos.size){
                Log.d("MyProviderTest", "for photo ${listOfPropertyToTest[i].photos[p]} and db ${db[i].photos[p]}")
                assertEquals(listOfPropertyToTest[i].photos[p], db[i].photos[p])
            }
        }
    }
}