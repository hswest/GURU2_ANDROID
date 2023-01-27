package com.example.guru2_android

import android.app.Application

class ApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()

        TodoRepository.initialize(this)
    }
}