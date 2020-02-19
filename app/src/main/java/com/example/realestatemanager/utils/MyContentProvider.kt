package com.example.realestatemanager.utils

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class MyContentProvider : ContentProvider() {

    companion object {
        const val ALL_PROPERTIES = 1
        const val ALL_PHOTOS = 2

        private const val AUTHORITY = "com.example.realestatemanager.utils"
        private val AUTHORITY_URI = Uri.parse("content://$AUTHORITY")

        const val PROPERTIES_PATH = "properties"
        const val PHOTOS_PATH = "photos"

        val PROPERTIES_URI: Uri = Uri.withAppendedPath(AUTHORITY_URI, PROPERTIES_PATH)
        val PHOTOS_URI : Uri = Uri.withAppendedPath(AUTHORITY_URI, PHOTOS_PATH)
    }

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    override fun onCreate(): Boolean {
        initializeUriMatching()
        return true
    }

    private fun initializeUriMatching(){
        uriMatcher.addURI(AUTHORITY, PROPERTIES_PATH, ALL_PROPERTIES)
        uriMatcher.addURI(AUTHORITY, PHOTOS_PATH, ALL_PHOTOS)
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {

        var cursor: Cursor? = null

        when (uriMatcher.match(uri)){
            ALL_PROPERTIES ->
                cursor = RealEstateDBHelper(context!!, null).getCursorOfProperties()

            ALL_PHOTOS ->
                cursor = RealEstateDBHelper(context!!, null).getCursorOfPhotos()
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