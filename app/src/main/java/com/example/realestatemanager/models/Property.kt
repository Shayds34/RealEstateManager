package com.example.realestatemanager.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Property(

    var type: String? = null,
    var place: String,
    var price: Int,
    var size: Int,
    var rooms: Int,
    var bathrooms: Int,
    var bedrooms: Int,
    var description: String,
    var address: String,
    var zipcode: String,
    var city: String,
    var country: String,
    var photos: List<String>,
    var pointOfInterest: String,
    var status: Boolean,
    var creationDate: String,
    var sellingDate: String,
    var author: String

) : Parcelable