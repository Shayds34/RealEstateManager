package com.example.realestatemanager

import com.example.realestatemanager.utils.Utils
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsTest {

    @Test
    fun convertDollarToEuroTest() {
        // Test 11/11/2019
        val dollars = 5
        val euros = Utils.convertDollarToEuro(dollars)

        assertEquals(4, euros)
    }

    @Test
    fun concertEuroToDollarTest(){
        // Test 16/11/2019
        val euros = 5
        val dollars = Utils.convertEuroToDollar(euros)

        assertEquals(6, dollars)
    }

    @Test
    fun getTodayDateTest() {
        // Test 11/11/2019
        val expectedTodayDate = "2019/11/16"

        assertEquals(expectedTodayDate, Utils.getTodayDate())
    }

    @Test
    fun convertTodayDate(){
        val expectedTodayDate = "16/11/2019"

        assertEquals(expectedTodayDate, Utils.getAnotherTodayDate())
    }
}