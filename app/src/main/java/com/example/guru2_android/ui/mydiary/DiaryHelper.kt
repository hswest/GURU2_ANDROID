package com.example.guru2_android.ui.mydiary

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DiaryHelper(context: MydiaryFragment) : SQLiteOpenHelper(context, "diary.db", null, VERSION) {

    companion object {
        private const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "create table diary(" +
                "title varchar(100) not null," +
                "content text not null," +
                "date char(10) not null" +
                ");"
        db?.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}