package com.example.realestatemanager

import com.example.realestatemanager.utils.Utils
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsTest {

    @Test
    fun convertDollarToEuroTest() {
        // Test passed 11/11/2019
        val dollars = 5
        val euros = Utils.convertDollarToEuro(dollars)

        assertEquals(4, euros)
    }

    @Test
    fun getTodayDateTest() {
        // Test passed 11/11/2019
        val expectedTodayDate = "2019/11/13"

        assertEquals(expectedTodayDate, Utils.getTodayDate())
    }




}