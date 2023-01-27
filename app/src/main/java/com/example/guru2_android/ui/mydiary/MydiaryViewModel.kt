package com.example.guru2_android.ui.mydiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MydiaryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "My Diary"
    }
    val text: LiveData<String> = _text
}