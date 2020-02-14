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

class RealEstateDBHelper (context: Context, cursorFactory: SQLiteDatabase.CursorFactory?) :
        SQLiteOpenHelper(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION) {

    private val myContext = context

    private val myTag = "RealEstateDBHelper"

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PROPERTIES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PHOTOS")
        onCreate(db)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_PROPERTIES_TABLE)
        db.execSQL(CREATE_PHOTOS_TABLE)
    }

    @Throws(SQLiteConstraintException::class)
    fun addProperty(property: Property){
        Log.d(myTag, "Try to add property to DB.")
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

        Log.d(myTag, "Property id ${property.id} has photos ${property.photos}")

        val db = this.writableDatabase

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
                Log.d(myTag, "Photo inserted.")
            } else {
                Log.d(myTag, "Something went wrong when inserting photo.")
            }
        }

        db.close()
    }

    @Throws(SQLiteConstraintException::class)
    fun editProperty(property: Property){

        // Makes db writable to update data
        val db = this.writableDatabase

        //#region {TABLE: properties = Update}
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

        db.update(TABLE_PROPERTIES, propertyValues, "_id=${property.id}", null)
        db.close()
        //endregion
    }

    @Throws(SQLiteConstraintException::class)
    fun addNewPhotos(photos: ArrayList<String>, property: Property){
        Log.d(myTag, "addNewPhotos $photos")
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
            Log.d(myTag, "Try")
            cursor = db.rawQuery("SELECT * FROM $TABLE_PROPERTIES", null)
            Log.d(myTag, cursor.toString())
        } catch (e: SQLException) {
            Log.d(myTag, e.toString())
            db.execSQL(CREATE_PROPERTIES_TABLE)
            db.execSQL(CREATE_PHOTOS_TABLE)
            return ArrayList()
        }

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
        var status : Boolean
        var creationDate : String
        var sellingDate : String
        var author : String

        if (cursor!!.moveToFirst()) {
            Log.d(myTag, "If")
            while (!cursor.isAfterLast){
                Log.d(myTag, "While")
                Log.d(myTag, cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)))

                id = cursor.getInt(cursor.getColumnIndex(COLUMN_PROPERTY_ID))
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
                photos = ArrayList()

                // Create list of photos from photos table from Database
                val photoCursor: Cursor? = db.rawQuery("SELECT * FROM $TABLE_PHOTOS WHERE $COLUMN_FK_ID_PROPERTY = $id", null)
                if (photoCursor!!.moveToFirst()){
                    while (!photoCursor.isAfterLast) {
                        Log.d(myTag, "Photo Cursor")
                        photos.add(photoCursor.getString(photoCursor.getColumnIndex(COLUMN_URI_PHOTOS)))

                        photoCursor.moveToNext()
                    }
                }
                photoCursor.close()

                pointOfInterest = ""
                status = true
                creationDate = cursor.getString(cursor.getColumnIndex(COLUMN_CREATION_DATE))
                sellingDate = cursor.getString(cursor.getColumnIndex(COLUMN_SELLING_DATE))
                author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR))

                // Add the property's row from SQLite Database to the list of Properties
                propertiesList.add(
                    Property(
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
                        author))

                cursor.moveToNext()
            }
        }

        cursor.close()
        return propertiesList
    }

    companion object {
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
        private const val COLUMN_ID_PHOTOS = "_id"
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