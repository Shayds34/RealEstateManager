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
                    17870000,
                    250,
                    7,
                    "Egergergerg ego senator inimicus, si ita vultis, homini, amicus esse, sicut semper fui, rei publicae debeo. Quid? si ipsas inimicitias, depono rei publicae causa, quis me tandem iure reprehendet, praesertim cum ego omnium meorum consiliorum atque factorum exempla semper ex summorum hominum consiliis atque factis mihi censuerim petenda.",
                    listOf("photo 1", "photo 2", "photo 3"),
                    listOf("School", "Restaurant", "Hospital"),
                    true,
                    "2019/11/14",
                    "2019/11/14",
                    "John"
                )
            )
            list.add(
                Property(
                    "House",
                    "Montauk",
                    21130000,
                    320,
                    9,
                    "FZasfasfaezfa ego senator inimicus, si ita vultis, homini, amicus esse, sicut semper fui, rei publicae debeo. Quid? si ipsas inimicitias, depono rei publicae causa, quis me tandem iure reprehendet, praesertim cum ego omnium meorum consiliorum atque factorum exempla semper ex summorum hominum consiliis atque factis mihi censuerim petenda.",
                    listOf("photo 1", "photo 2", "photo 3"),
                    listOf("School", "Restaurant", "Hospital"),
                    true,
                    "2019/11/14",
                    "2019/11/14",
                    "John"
                )
            )
            list.add(
                Property(
                    "Duplex",
                    "Brooklyn",
                    13990000,
                    250,
                    7,
                    "Fazlokvfpaek ego senator inimicus, si ita vultis, homini, amicus esse, sicut semper fui, rei publicae debeo. Quid? si ipsas inimicitias, depono rei publicae causa, quis me tandem iure reprehendet, praesertim cum ego omnium meorum consiliorum atque factorum exempla semper ex summorum hominum consiliis atque factis mihi censuerim petenda.",
                    listOf("photo 1", "photo 2", "photo 3"),
                    listOf("School", "Restaurant", "Hospital"),
                    true,
                    "2019/11/14",
                    "2019/11/14",
                    "John"
                )
            )
            list.add(
                Property(
                    "House",
                    "Southampton",
                    41480000,
                    250,
                    7,
                    "Mpipifzefze ego senator inimicus, si ita vultis, homini, amicus esse, sicut semper fui, rei publicae debeo. Quid? si ipsas inimicitias, depono rei publicae causa, quis me tandem iure reprehendet, praesertim cum ego omnium meorum consiliorum atque factorum exempla semper ex summorum hominum consiliis atque factis mihi censuerim petenda.",
                    listOf("photo 1", "photo 2", "photo 3"),
                    listOf("School", "Restaurant", "Hospital"),
                    true,
                    "2019/11/14",
                    "2019/11/14",
                    "John"
                )
            )
            list.add(
                Property(
                    "Penthouse",
                    "Upper East Side",
                    29872000,
                    250,
                    7,
                    "Bnhntnt ego senator inimicus, si ita vultis, homini, amicus esse, sicut semper fui, rei publicae debeo. Quid? si ipsas inimicitias, depono rei publicae causa, quis me tandem iure reprehendet, praesertim cum ego omnium meorum consiliorum atque factorum exempla semper ex summorum hominum consiliis atque factis mihi censuerim petenda.",
                    listOf("photo 1", "photo 2", "photo 3"),
                    listOf("School", "Restaurant", "Hospital"),
                    true,
                    "2019/11/14",
                    "2019/11/14",
                    "John"
                )
            )
            list.add(
                Property(
                    "House",
                    "Hampton Bays",
                    44220000,
                    250,
                    7,
                    "§Ouoeruterte ego senator inimicus, si ita vultis, homini, amicus esse, sicut semper fui, rei publicae debeo. Quid? si ipsas inimicitias, depono rei publicae causa, quis me tandem iure reprehendet, praesertim cum ego omnium meorum consiliorum atque factorum exempla semper ex summorum hominum consiliis atque factis mihi censuerim petenda.",
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