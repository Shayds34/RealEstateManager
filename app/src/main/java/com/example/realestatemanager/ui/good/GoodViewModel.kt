package com.example.realestatemanager.ui.good

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GoodViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is good Fragment"
    }
    val text: LiveData<String> = _text
}