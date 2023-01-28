package com.example.guru2_android

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Diary::class), version = 1)
abstract class DiaryDB: RoomDatabase() {
    abstract fun diaryDao(): DiaryDAO
}