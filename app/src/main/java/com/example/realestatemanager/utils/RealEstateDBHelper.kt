package com.example.realestatemanager.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.realestatemanager.models.Property

class RealEstateDBHelper (context: Context, cursorFactory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION) {

    private val myContext = context

    private lateinit var queryString : String
    private lateinit var queryTerms : ArrayList<String>
    private var mCount : Int = 0

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PROPERTIES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PHOTOS")
        onCreate(db)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_PROPERTIES_TABLE)
        db.execSQL(CREATE_PHOTOS_TABLE)
    }

    // TODO Finish this function      
    @Throws(SQLiteConstraintException::class)
    fun getCountFromAuthor(author: String) : Int {
        val db = this.readableDatabase
        val cursor: Cursor?

        mCount = 0
        try {
            cursor =  db.rawQuery("SELECT COUNT($COLUMN_PROPERTY_ID) FROM $TABLE_PROPERTIES WHERE $COLUMN_AUTHOR = $author", null)
        } catch (e: SQLException) {
            Log.d(TAG, e.toString())
            db.execSQL(CREATE_PROPERTIES_TABLE)
            db.execSQL(CREATE_PHOTOS_TABLE)
            return 0
        }
        cursor.close()
        return mCount
    }

    @Throws(SQLiteConstraintException::class)
    fun addProperty(property: Property){
        val db = this.writableDatabase
        val propertyValues = editContentValues(property)

        // Check if row was added and notify user.
        val rowInserted = db.insert(TABLE_PROPERTIES, null, propertyValues)
        if (rowInserted.toInt() != -1) {
            Toast.makeText(myContext, "New row added to the database, id: $rowInserted.", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(myContext, "Something went wrong.", Toast.LENGTH_LONG).show()
        }

        val photoValues = ContentValues()
        for (photo in property.photos) {
            photoValues.put(COLUMN_FK_ID_PROPERTY, rowInserted)
            photoValues.put(COLUMN_URI_PHOTOS, photo)

            val photoInserted = db.insert(TABLE_PHOTOS, null, photoValues)

            if (photoInserted.toInt() != -1){
                Log.d(TAG, "Photo inserted.")
            } else {
                Log.d(TAG, "Something went wrong when inserting photo.")
            }
        }
        db.close()
    }

    private fun editContentValues(property: Property): ContentValues {
        val propertyValues = ContentValues()

        propertyValues.put(COLUMN_TYPE, property.type)
        propertyValues.put(COLUMN_NEIGHBORHOOD, property.neighborhood)
        propertyValues.put(COLUMN_PRICE, property.price)
        propertyValues.put(COLUMN_SIZE, property.size)
        propertyValues.put(COLUMN_ROOMS, property.rooms)
        propertyValues.put(COLUMN_BATHROOMS, property.bathrooms)
        propertyValues.put(COLUMN_BEDROOMS, property.bedrooms)
        propertyValues.put(COLUMN_DESCRIPTION, property.description)
        propertyValues.put(COLUMN_ADDRESS, property.address)
        propertyValues.put(COLUMN_ZIP_CODE, property.zip_code)
        propertyValues.put(COLUMN_CITY, property.city)
        propertyValues.put(COLUMN_COUNTRY, property.country)
        propertyValues.put(COLUMN_STATUS, property.status)
        propertyValues.put(COLUMN_CREATION_DATE, property.creationDate)
        propertyValues.put(COLUMN_SELLING_DATE, property.sellingDate)
        propertyValues.put(COLUMN_AUTHOR, property.author)
        propertyValues.put(COLUMN_POINT_OF_INTEREST, property.pointOfInterest)

        return propertyValues
    }

    @Throws(SQLiteConstraintException::class)
    fun editProperty(property: Property){
        val db = this.writableDatabase
        val propertyValues = editContentValues(property)
        db.update(TABLE_PROPERTIES, propertyValues, "_id=${property.id}", null)
        db.close()
    }

    @Throws(SQLiteConstraintException::class)
    fun addNewPhotos(photos: ArrayList<String>, property: Property){
        Log.d(TAG, "addNewPhotos $photos")
        val db = this.writableDatabase
        val photoValues = ContentValues()

        for (photo in photos) {
            photoValues.put(COLUMN_FK_ID_PROPERTY, property.id)
            photoValues.put(COLUMN_URI_PHOTOS, photo)

            db.insert(TABLE_PHOTOS, null, photoValues)
        }
        db.close()
    }

    @Throws(SQLiteConstraintException::class)
    fun deletePhotos(photosToBeDeleted: ArrayList<String>, property: Property){
        // Makes db writable to update data
        val db = this.writableDatabase

        val table = TABLE_PHOTOS
        val whereClause = "$COLUMN_FK_ID_PROPERTY=? and $COLUMN_URI_PHOTOS=?"

        for (photo in photosToBeDeleted) {
            val whereArgs = arrayOf(property.id.toString(), photo)
            db.delete(table, whereClause, whereArgs)

        }
        db.close()
    }

    @Throws(SQLiteConstraintException::class)
    fun getListOfProperties(): ArrayList<Property> {
        val propertiesList = ArrayList<Property>()

        val db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_PROPERTIES", null)
            Log.d(TAG, cursor.toString())
        } catch (e: SQLException) {
            Log.d(TAG, e.toString())
            db.execSQL(CREATE_PROPERTIES_TABLE)
            db.execSQL(CREATE_PHOTOS_TABLE)
            return ArrayList()
        }

        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast){
                // Add the property's row from SQLite Database to the list of Properties
                val property = newPropertyFromCursor(cursor)
                propertiesList.add(property)
                cursor.moveToNext()
            }
        }
        cursor.close()
        return propertiesList
    }

    // SearchActivity -> SearchPropertiesActivity
    @Throws(SQLiteConstraintException::class)
    fun getListOfSearchedProperties(minPrice: Int, maxPrice: Int, chipsType: ArrayList<String>, photosCount: String): ArrayList<Property> {
        val propertiesList = ArrayList<Property>()
        val db = this.readableDatabase
        val cursor: Cursor?

        queryString = "SELECT * FROM $TABLE_PROPERTIES"
        queryTerms = ArrayList()

        val minPriceString : String
        if (minPrice > 0) {
            minPriceString = "$COLUMN_PRICE >= $minPrice"
            queryTerms.add(minPriceString)
        }

        val maxPriceString : String
        if (maxPrice > 0) {
            maxPriceString = "$COLUMN_PRICE <= $maxPrice"
            queryTerms.add(maxPriceString)
        }

        val typeString : String
        if (chipsType.size > 0) {
            val list = chipsType.joinToString(prefix = "'", separator = "', '", postfix = "'")
            typeString = "$COLUMN_TYPE IN ($list)"
            queryTerms.add(typeString)
        }

        if (queryTerms.size > 0) {
            queryString = queryString.plus(queryTerms.joinToString(prefix = " WHERE ", separator = " AND ", postfix = " ORDER BY $COLUMN_PRICE"))
        }

        try {
            cursor = db.rawQuery(queryString, null)
        } catch (e: SQLException) {
            Log.d(TAG, e.toString())
            db.execSQL(CREATE_PROPERTIES_TABLE)
            db.execSQL(CREATE_PHOTOS_TABLE)
            return ArrayList()
        }

        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast){
                val property = newPropertyFromCursor(cursor)

                if  (photosCount == "more") {
                    if (property.photos.size >= 5){
                        // Add the property's row from SQLite Database to the list of Property
                        propertiesList.add(property)
                    }
                } else if (property.photos.size >= Integer.parseInt(photosCount)){
                    // Add the property's row from SQLite Database to the list of Property
                    // when the number of photos is sup or equal photosCount (from SearchActivity)
                    propertiesList.add(property)
                }
                cursor.moveToNext()
            }
        }

        cursor.close()
        return propertiesList
    }

    private fun newPropertyFromCursor(cursor: Cursor): Property {
        val property = Property()

        property.id = cursor.getInt(cursor.getColumnIndex(COLUMN_PROPERTY_ID))
        property.type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE))
        property.neighborhood = cursor.getString(cursor.getColumnIndex(COLUMN_NEIGHBORHOOD))
        property.price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE))
        property.size = cursor.getString(cursor.getColumnIndex(COLUMN_SIZE))
        property.rooms = cursor.getString(cursor.getColumnIndex(COLUMN_ROOMS))
        property.bathrooms = cursor.getString(cursor.getColumnIndex(COLUMN_BATHROOMS))
        property.bedrooms = cursor.getString(cursor.getColumnIndex(COLUMN_BEDROOMS))
        property.description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
        property.address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS))
        property.zip_code = cursor.getString(cursor.getColumnIndex(COLUMN_ZIP_CODE))
        property.city = cursor.getString(cursor.getColumnIndex(COLUMN_CITY))
        property.country = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY))
        property.photos = ArrayList()

        // Create list of photos from photos table from Database
        val db = this.readableDatabase
        val photoCursor: Cursor? = db.rawQuery("SELECT * FROM $TABLE_PHOTOS WHERE $COLUMN_FK_ID_PROPERTY = ${property.id}", null)
        if (photoCursor!!.moveToFirst()){
            while (!photoCursor.isAfterLast) {
                property.photos.add(photoCursor.getString(photoCursor.getColumnIndex(COLUMN_URI_PHOTOS)))
                photoCursor.moveToNext()
            }
        }
        photoCursor.close()

        property.pointOfInterest = ""
        property.status = ""
        property.creationDate = cursor.getString(cursor.getColumnIndex(COLUMN_CREATION_DATE))
        property.sellingDate = cursor.getString(cursor.getColumnIndex(COLUMN_SELLING_DATE))
        property.author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR))

        return property
    }

    // For Content Provider
    @Throws(SQLiteConstraintException::class)
    fun getCursorOfProperties(): Cursor? {
        val db = this.readableDatabase
        return db.query(
            TABLE_PROPERTIES,
            arrayOf(
                COLUMN_PROPERTY_ID,
                COLUMN_TYPE,
                COLUMN_NEIGHBORHOOD,
                COLUMN_PRICE,
                COLUMN_SIZE,
                COLUMN_ROOMS,
                COLUMN_BATHROOMS,
                COLUMN_BEDROOMS,
                COLUMN_DESCRIPTION,
                COLUMN_ADDRESS,
                COLUMN_ZIP_CODE,
                COLUMN_CITY,
                COLUMN_COUNTRY,
                COLUMN_STATUS,
                COLUMN_CREATION_DATE,
                COLUMN_SELLING_DATE,
                COLUMN_AUTHOR,
                COLUMN_POINT_OF_INTEREST
            ),
            null,
            null,
            null,
            null,
            null,
            null)
    }

    // For Content Provider
    @Throws(SQLiteConstraintException::class)
    fun getCursorOfPhotos(): Cursor? {
        val db = this.readableDatabase
        return db.query(
            TABLE_PHOTOS,
            arrayOf(COLUMN_ID_PHOTOS,
                COLUMN_FK_ID_PROPERTY,
                COLUMN_URI_PHOTOS),
            null,
            null,
            null,
            null,
            null,
            null)
    }

    companion object {
        private const val TAG = "RealEstateDBHelper"
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "realestatemanager.db"

        //region {TABLE PROPERTIES}
        const val TABLE_PROPERTIES = "properties"

        const val COLUMN_PROPERTY_ID = "_id"

        const val COLUMN_TYPE = "type"
        const val COLUMN_NEIGHBORHOOD = "neighborhood"
        const val COLUMN_PRICE = "price"
        const val COLUMN_SIZE = "size"
        const val COLUMN_ROOMS = "rooms"
        const val COLUMN_BATHROOMS = "bathrooms"
        const val COLUMN_BEDROOMS = "bedrooms"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_ZIP_CODE = "zip_code"
        const val COLUMN_CITY = "city"
        const val COLUMN_COUNTRY = "country"
        const val COLUMN_STATUS = "status"
        const val COLUMN_CREATION_DATE = "creation_date"
        const val COLUMN_SELLING_DATE = "selling_date"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_POINT_OF_INTEREST = "point_of_interest"

        const val CREATE_PROPERTIES_TABLE = "CREATE TABLE " +
                "$TABLE_PROPERTIES (" +
                "$COLUMN_PROPERTY_ID INTEGER PRIMARY KEY," +
                "$COLUMN_TYPE TEXT," +
                "$COLUMN_NEIGHBORHOOD TEXT," +
                "$COLUMN_PRICE TEXT," +
                "$COLUMN_SIZE TEXT," +
                "$COLUMN_ROOMS TEXT," +
                "$COLUMN_BATHROOMS TEXT," +
                "$COLUMN_BEDROOMS TEXT," +
                "$COLUMN_DESCRIPTION TEXT," +
                "$COLUMN_ADDRESS TEXT," +
                "$COLUMN_ZIP_CODE TEXT," +
                "$COLUMN_CITY TEXT," +
                "$COLUMN_COUNTRY TEXT," +
                "$COLUMN_STATUS TEXT," +
                "$COLUMN_CREATION_DATE DATE," +
                "$COLUMN_SELLING_DATE DATE," +
                "$COLUMN_AUTHOR TEXT," +
                "$COLUMN_POINT_OF_INTEREST TEXT)"
        //endregion

        //region {TABLE PHOTOS}
        const val TABLE_PHOTOS = "photos"
        const val COLUMN_ID_PHOTOS = "_id"
        const val COLUMN_FK_ID_PROPERTY = "property_id"
        const val COLUMN_URI_PHOTOS = "photo_uri"

        const val CREATE_PHOTOS_TABLE = "CREATE TABLE " +
                "$TABLE_PHOTOS (" +
                "$COLUMN_ID_PHOTOS INTEGER PRIMARY KEY," +
                "$COLUMN_FK_ID_PROPERTY INTEGER," +
                "$COLUMN_URI_PHOTOS TEXT," +
                "FOREIGN KEY($COLUMN_FK_ID_PROPERTY) " +
                "REFERENCES $TABLE_PROPERTIES($COLUMN_PROPERTY_ID)" +
                ")"
        //endregion
    }
}
