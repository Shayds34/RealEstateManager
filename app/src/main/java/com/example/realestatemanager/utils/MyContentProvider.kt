package com.example.realestatemanager.utils

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.example.realestatemanager.models.Property

class MyContentProvider : ContentProvider() {

    companion object {
        const val ALL_PROPERTIES = 1

        private const val AUTHORITY = "com.example.realestatemanager.utils"
        const val CONTENT_PATH = "properties"

        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$CONTENT_PATH")
    }

    private var mData : ArrayList<Property>? = null
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    override fun onCreate(): Boolean {
        initializeUriMatching()
        val context = context
        mData = RealEstateDBHelper(context!!, null).getListOfProperties()
        // TODO getPhotos()
        return true
    }

    private fun initializeUriMatching(){
        uriMatcher.addURI(AUTHORITY, CONTENT_PATH, 0)
        uriMatcher.addURI(AUTHORITY, "$CONTENT_PATH/#", 1)
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        var id: Int
        when (uriMatcher.match(uri)){
            0 -> {
                id = 1
                if (selection != null){
                    id = (selectionArgs!![0]).toInt()
                }
            }
            UriMatcher.NO_MATCH -> {
                id = -1
            } else -> {
            id = -1
            }
        }
        return populateCursor(id)
    }

    private fun populateCursor (id : Int) : Cursor {
        val cursor = MatrixCursor(arrayOf(CONTENT_PATH))

        if (id == ALL_PROPERTIES) {
            for (i in 0 until mData!!.size){
                val property = mData!![i]
                cursor.addRow(arrayOf(property))
            }
        } else if (id >= 0) {
            val property = mData!![id]
            cursor.addRow(arrayOf(property))
        }
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

}