package com.example.realestatemanager.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.realestatemanager.models.Property

class RealEstateDBHelper (context: Context, cursorFactory: SQLiteDatabase.CursorFactory?) :
        SQLiteOpenHelper(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION) {

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_TYPE TEXT," +
                "$COLUMN_PRICE INTEGER," +
                "$COLUMN_SIZE INTEGER," +
                "$COLUMN_ROOMS INTEGER," +
                "$COLUMN_BATHROOMS INTEGER," +
                "$COLUMN_BEDROOMS INTEGER," +
                "$COLUMN_DESCRIPTION TEXT," +
                "$COLUMN_ADDRESS TEXT," +
                "$COLUMN_ZIPCODE TEXT," +
                "$COLUMN_CITY TEXT," +
                "$COLUMN_COUNTRY TEXT," +
                "$COLUMN_STATUS TEXT," +
                "$COLUMN_CREATION_DATE DATE," +
                "$COLUMN_SELLING_DATE DATE," +
                "$COLUMN_AUTHOR TEXT," +
                "$COLUMN_POINT_OF_INTEREST TEXT)"
        db?.execSQL(createTable)
    }

    fun addProperty(property: Property){
        val contentValues = ContentValues()

        contentValues.put(COLUMN_TYPE, property.type)
        contentValues.put(COLUMN_PRICE, property.price)
        contentValues.put(COLUMN_SIZE, property.size)
        contentValues.put(COLUMN_ROOMS, property.rooms)
        contentValues.put(COLUMN_BATHROOMS, property.bathrooms)
        contentValues.put(COLUMN_BEDROOMS, property.bedrooms)
        contentValues.put(COLUMN_DESCRIPTION, property.description)
        contentValues.put(COLUMN_ADDRESS, property.address)
        contentValues.put(COLUMN_ZIPCODE, property.zipcode)
        contentValues.put(COLUMN_CITY, property.city)
        contentValues.put(COLUMN_COUNTRY, property.country)
        contentValues.put(COLUMN_STATUS, property.status)
        contentValues.put(COLUMN_CREATION_DATE, property.creationDate)
        contentValues.put(COLUMN_SELLING_DATE, property.sellingDate)
        contentValues.put(COLUMN_AUTHOR, property.author)
        contentValues.put(COLUMN_POINT_OF_INTEREST, property.pointOfInterest)

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

    fun getAllProperties(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "realestatemanager.db"
        const val TABLE_NAME = "property"
        const val COLUMN_ID = "_id"
        const val COLUMN_TYPE = "type"
        const val COLUMN_PRICE = "price"
        const val COLUMN_SIZE = "size"
        const val COLUMN_ROOMS = "rooms"
        const val COLUMN_BATHROOMS = "bathrooms"
        const val COLUMN_BEDROOMS = "bedrooms"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_ZIPCODE = "zipcode"
        const val COLUMN_CITY = "city"
        const val COLUMN_COUNTRY = "country"
        const val COLUMN_STATUS = "status"
        const val COLUMN_CREATION_DATE = "creation_date"
        const val COLUMN_SELLING_DATE = "selling_date"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_POINT_OF_INTEREST = "point_of_interest"
    }
}