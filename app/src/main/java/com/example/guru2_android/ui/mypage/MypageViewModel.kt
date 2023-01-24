package com.example.guru2_android.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MypageViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "My Information"
    }
    val text: LiveData<String> = _text
}