package com.example.guru2_android

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel: ViewModel() {
    private val todoRepository: TodoRepository = TodoRepository.get()

    fun list(toDoDate: String) = todoRepository.list(toDoDate)

    fun getOne(id: Long) = todoRepository.getOne(id)

    fun insert(dto: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.insert(dto)
    }

    fun update(dto: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.update(dto)
    }

    fun delete(id: Long) = todoRepository.delete(id)

}