package com.example.guru2_android

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DiaryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(diary: Diary)

    @Query("select * from diaryTable where date = :date")
    fun list(date: String): LiveData<MutableList<Diary>>

//    @Query("select * from todoTable where id = (:id)")
//    fun getOne(id: Long): Todo

    @Query("select * from diaryTable where date = (:date)")
    fun selectByDate(date: String): Diary

    @Update
    suspend fun update(diary: Diary)

    @Query("delete from diaryTable where id = (:id)")
    fun delete(id: Long)
}