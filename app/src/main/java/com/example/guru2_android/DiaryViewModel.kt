package com.example.guru2_android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryViewModel: ViewModel() {

    private val diaryRepository: DiaryRepository = DiaryRepository.get()


    fun list(date: String) = diaryRepository.list(date)

    //fun getOne(id: Long) = diaryRepository.getOne(id)

    fun getByDate(date: String) = diaryRepository.getByDate(date)

    fun insert(diary: Diary) = viewModelScope.launch(Dispatchers.IO) {
        diaryRepository.insert(diary)
    }

    fun update(diary: Diary) = viewModelScope.launch(Dispatchers.IO) {
        diaryRepository.update(diary)
    }

    fun delete(id: Long) = diaryRepository.delete(id)

}