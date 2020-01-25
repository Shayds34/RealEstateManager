package com.example.realestatemanager

import com.example.realestatemanager.utils.Utils
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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
        val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd")
        val expectedTodayDate = dateFormat.format(Date())

        assertEquals(expectedTodayDate, Utils.getTodayDate())
    }

    @Test
    fun convertTodayDate(){
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        val expectedTodayDate = dateFormat.format(Date())

        assertEquals(expectedTodayDate, Utils.getAnotherTodayDate())
    }
}