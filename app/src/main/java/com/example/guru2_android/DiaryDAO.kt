package com.example.guru2_android

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DiaryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(diary: Diary)

    @Query("select * from diaryTable where date = :date")
    fun list(date: String): LiveData<MutableList<Diary>>
}