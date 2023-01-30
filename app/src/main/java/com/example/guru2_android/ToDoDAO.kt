package com.example.guru2_android

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dto: Todo)

    @Query("select * from todoTable where toDoDate = :toDoDate")
    fun list(toDoDate: String): LiveData<MutableList<Todo>>

    @Query("select * from todoTable where id = (:id)")
    fun getOne(id: Long): Todo

    @Update
    suspend fun update(dto: Todo)

    @Query("delete from todoTable where id = (:id)")
    fun delete(id: Long)
}