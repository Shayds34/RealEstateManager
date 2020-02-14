package com.example.realestatemanager.utils

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class MyContentProvider : ContentProvider() {

    companion object {
        const val ALL_PROPERTIES = 1

        private const val AUTHORITY = "com.example.realestatemanager.utils"
        private val AUTHORITY_URI = Uri.parse("content://$AUTHORITY")
        const val CONTENT_PATH = "properties"

        val CONTENT_URI: Uri = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH)
    }

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    override fun onCreate(): Boolean {
        initializeUriMatching()
        return true
    }

    private fun initializeUriMatching(){
        uriMatcher.addURI(AUTHORITY, CONTENT_PATH, ALL_PROPERTIES)
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {

        return RealEstateDBHelper(context!!, null).getCursorOfProperties()
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