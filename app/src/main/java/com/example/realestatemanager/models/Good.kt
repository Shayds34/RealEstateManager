package com.example.realestatemanager.com.example.realestatemanager.models

import java.util.*

data class Good(

    var type: String,
    var place: String,
    var price: Int,
    var size: Int,
    var rooms: Int,
    var description: String,
    var photos: List<String>,
    var pointOfInterest: List<Any>,
    var status: Boolean,
    var creationDate: String,
    var sellingDate: String,
    var author: String
) {

}