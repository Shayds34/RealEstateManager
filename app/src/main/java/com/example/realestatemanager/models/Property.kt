package com.example.realestatemanager.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Property(
    var type: String,
    var neighborhood: String,
    var price: String,
    var size: String,
    var rooms: String,
    var bathrooms: String,
    var bedrooms: String,
    var description: String,
    var address: String,
    var zip_code: String,
    var city: String,
    var country: String,
    var photos: List<String>,
    var pointOfInterest: String,
    var status: Boolean,
    var creationDate: String,
    var sellingDate: String,
    var author: String
) : Parcelable

