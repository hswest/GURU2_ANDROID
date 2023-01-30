package com.example.guru2_android

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todoTable")
class Todo(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "toDoDate") val toDoDate: String,
    @ColumnInfo(name = "isChecked") var isChecked: Boolean,
    @ColumnInfo(name = "isClicked") var isClicked: Boolean
)