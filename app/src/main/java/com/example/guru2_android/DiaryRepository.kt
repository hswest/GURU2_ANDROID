package com.example.guru2_android

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room

private const val DATABASE_NAME = "diary-database.db"
class DiaryRepository private constructor(context: Context){

    private val database: DiaryDB = Room.databaseBuilder(
        context.applicationContext,
        DiaryDB::class.java,
        DATABASE_NAME
    ).build()

    private val diaryDao = database.diaryDao()

    fun list(date: String): LiveData<MutableList<Diary>> = diaryDao.list(date)

    //fun getOne(id: Long): Todo = diaryDao.getOne(id)

    fun getByDate(date: String): Diary = diaryDao.selectByDate(date)

    fun insert(diary: Diary) = diaryDao.insert(diary)

    suspend fun update(diary: Diary) = diaryDao.update(diary)

    fun delete(id: Long) = diaryDao.delete(id)

    companion object {
        private var INSTANCE: DiaryRepository?=null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = DiaryRepository(context)
            }
        }

        fun get(): DiaryRepository {
            return INSTANCE ?:
            throw IllegalStateException("DiaryRepository must be initialized")
        }
    }
}