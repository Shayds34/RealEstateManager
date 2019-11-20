package com.example.realestatemanager.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.realestatemanager.models.Property

class RealEstateDBHelper (context: Context, cursorFactory: SQLiteDatabase.CursorFactory?) :
        SQLiteOpenHelper(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION) {

    private val myTag = "RealEstateDBHelper"

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    @Throws(SQLiteConstraintException::class)
    fun addProperty(property: Property){
        Log.d(myTag, "Try to add property to DB.")
        val contentValues = ContentValues()

        contentValues.put(COLUMN_TYPE, property.type)
        contentValues.put(COLUMN_NEIGHBORHOOD, property.neighborhood)
        contentValues.put(COLUMN_PRICE, property.price)
        contentValues.put(COLUMN_SIZE, property.size)
        contentValues.put(COLUMN_ROOMS, property.rooms)
        contentValues.put(COLUMN_BATHROOMS, property.bathrooms)
        contentValues.put(COLUMN_BEDROOMS, property.bedrooms)
        contentValues.put(COLUMN_DESCRIPTION, property.description)
        contentValues.put(COLUMN_ADDRESS, property.address)
        contentValues.put(COLUMN_ZIP_CODE, property.zip_code)
        contentValues.put(COLUMN_CITY, property.city)
        contentValues.put(COLUMN_COUNTRY, property.country)
        contentValues.put(COLUMN_STATUS, property.status)
        contentValues.put(COLUMN_CREATION_DATE, property.creationDate)
        contentValues.put(COLUMN_SELLING_DATE, property.sellingDate)
        contentValues.put(COLUMN_AUTHOR, property.author)
        contentValues.put(COLUMN_POINT_OF_INTEREST, property.pointOfInterest)

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, contentValues)
        Log.d(myTag, "Property added to DB.")
        db.close()
    }

    @Throws(SQLiteConstraintException::class)
    fun getListOfProperties(): ArrayList<Property> {
        val propertiesList = ArrayList<Property>()

        val db = this.readableDatabase
        val cursor: Cursor?

        try {
            Log.d(myTag, "Try")
            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
            Log.d(myTag, cursor.toString())
        } catch (e: SQLException) {
            Log.d(myTag, e.toString())
            db.execSQL(CREATE_TABLE)
            return ArrayList()
        }

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
        var photos : List<String>
        var pointOfInterest : String
        var status : Boolean
        var creationDate : String
        var sellingDate : String
        var author : String

        if (cursor!!.moveToFirst()) {
            Log.d(myTag, "If")
            while (!cursor.isAfterLast){
                Log.d(myTag, "While")
                Log.d(myTag, cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)))

                type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE))
                neighborhood = cursor.getString(cursor.getColumnIndex(COLUMN_NEIGHBORHOOD))
                price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE))
                size = cursor.getString(cursor.getColumnIndex(COLUMN_SIZE))
                rooms = cursor.getString(cursor.getColumnIndex(COLUMN_ROOMS))
                bathrooms = cursor.getString(cursor.getColumnIndex(COLUMN_BATHROOMS))
                bedrooms = cursor.getString(cursor.getColumnIndex(COLUMN_BEDROOMS))
                description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS))
                zipCode = cursor.getString(cursor.getColumnIndex(COLUMN_ZIP_CODE))
                city = cursor.getString(cursor.getColumnIndex(COLUMN_CITY))
                country = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY))
                photos = listOf("")
                pointOfInterest = ""
                status = true
                creationDate = cursor.getString(cursor.getColumnIndex(COLUMN_CREATION_DATE))
                sellingDate = cursor.getString(cursor.getColumnIndex(COLUMN_SELLING_DATE))
                author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR))

                // Add the property's row from SQLite Database to the list of Properties
                propertiesList.add(
                    Property(
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
                        author))

                cursor.moveToNext()
            }
        }

        cursor.close()
        return propertiesList
    }

    @Throws(SQLiteConstraintException::class)
    fun updateProperty(property: Property){
        // TODO

    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "realestatemanager.db"
        const val TABLE_NAME = "property"
        private const val COLUMN_ID = "_id"
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

        const val CREATE_TABLE = "CREATE TABLE " +
                "$TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
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
    }
}