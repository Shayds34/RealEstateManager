package com.example.realestatemanager.utils

import com.example.realestatemanager.models.Property

class DataSource {

    companion object{
        fun createDataSet(): ArrayList<Property>{
            val list = ArrayList<Property>()
            list.add(
                Property(
                    "Flat",
                    "Manhattan",
                    "17870000",
                    "250",
                    "7",
                    "2",
                    "4",
                    "Egergergerg ego senator inimicus, si ita vultis, homini, amicus esse, sicut semper fui, rei publicae debeo. Quid? si ipsas inimicitias, depono rei publicae causa, quis me tandem iure reprehendet, praesertim cum ego omnium meorum consiliorum atque factorum exempla semper ex summorum hominum consiliis atque factis mihi censuerim petenda.",
                    "261 rue le Tintoret",
                    "34000",
                    "Montpellier",
                    "France",
                    listOf("photo 1", "photo 2", "photo 3"),
                    "School, Restaurant, Hospital",
                    true,
                    "2019/11/14",
                    "2019/11/14",
                    "John"
                )
            )
            return list
        }
    }
}