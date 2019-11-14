package com.example.realestatemanager.com.example.realestatemanager

import com.example.realestatemanager.com.example.realestatemanager.models.Good

class DataSource {

    companion object{
        fun createDataSet(): ArrayList<Good>{
            val list = ArrayList<Good>()
            list.add(
                Good(
                    "Flat",
                    "Manhattan",
                    17870000,
                    250,
                    7,
                    "Ergo ego senator inimicus, si ita vultis, homini, amicus esse, sicut semper fui, rei publicae debeo. Quid? si ipsas inimicitias, depono rei publicae causa, quis me tandem iure reprehendet, praesertim cum ego omnium meorum consiliorum atque factorum exempla semper ex summorum hominum consiliis atque factis mihi censuerim petenda.",
                    listOf("photo 1", "photo 2", "photo 3"),
                    listOf("School", "Restaurant", "Hospital"),
                    true,
                    "2019/11/14",
                    "2019/11/14",
                    "John"
                )
            )
            list.add(
                Good(
                    "House",
                    "Montauk",
                    21130000,
                    320,
                    9,
                    "Ergo ego senator inimicus, si ita vultis, homini, amicus esse, sicut semper fui, rei publicae debeo. Quid? si ipsas inimicitias, depono rei publicae causa, quis me tandem iure reprehendet, praesertim cum ego omnium meorum consiliorum atque factorum exempla semper ex summorum hominum consiliis atque factis mihi censuerim petenda.",
                    listOf("photo 1", "photo 2", "photo 3"),
                    listOf("School", "Restaurant", "Hospital"),
                    true,
                    "2019/11/14",
                    "2019/11/14",
                    "John"
                )
            )
            list.add(
                Good(
                    "Duplex",
                    "Brooklyn",
                    13990000,
                    250,
                    7,
                    "Ergo ego senator inimicus, si ita vultis, homini, amicus esse, sicut semper fui, rei publicae debeo. Quid? si ipsas inimicitias, depono rei publicae causa, quis me tandem iure reprehendet, praesertim cum ego omnium meorum consiliorum atque factorum exempla semper ex summorum hominum consiliis atque factis mihi censuerim petenda.",
                    listOf("photo 1", "photo 2", "photo 3"),
                    listOf("School", "Restaurant", "Hospital"),
                    true,
                    "2019/11/14",
                    "2019/11/14",
                    "John"
                )
            )
            list.add(
                Good(
                    "House",
                    "Southampton",
                    41480000,
                    250,
                    7,
                    "Ergo ego senator inimicus, si ita vultis, homini, amicus esse, sicut semper fui, rei publicae debeo. Quid? si ipsas inimicitias, depono rei publicae causa, quis me tandem iure reprehendet, praesertim cum ego omnium meorum consiliorum atque factorum exempla semper ex summorum hominum consiliis atque factis mihi censuerim petenda.",
                    listOf("photo 1", "photo 2", "photo 3"),
                    listOf("School", "Restaurant", "Hospital"),
                    true,
                    "2019/11/14",
                    "2019/11/14",
                    "John"
                )
            )
            list.add(
                Good(
                    "Penthouse",
                    "Upper East Side",
                    29872000,
                    250,
                    7,
                    "Ergo ego senator inimicus, si ita vultis, homini, amicus esse, sicut semper fui, rei publicae debeo. Quid? si ipsas inimicitias, depono rei publicae causa, quis me tandem iure reprehendet, praesertim cum ego omnium meorum consiliorum atque factorum exempla semper ex summorum hominum consiliis atque factis mihi censuerim petenda.",
                    listOf("photo 1", "photo 2", "photo 3"),
                    listOf("School", "Restaurant", "Hospital"),
                    true,
                    "2019/11/14",
                    "2019/11/14",
                    "John"
                )
            )
            list.add(
                Good(
                    "House",
                    "Hampton Bays",
                    44220000,
                    250,
                    7,
                    "Ergo ego senator inimicus, si ita vultis, homini, amicus esse, sicut semper fui, rei publicae debeo. Quid? si ipsas inimicitias, depono rei publicae causa, quis me tandem iure reprehendet, praesertim cum ego omnium meorum consiliorum atque factorum exempla semper ex summorum hominum consiliis atque factis mihi censuerim petenda.",
                    listOf("photo 1", "photo 2", "photo 3"),
                    listOf("School", "Restaurant", "Hospital"),
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