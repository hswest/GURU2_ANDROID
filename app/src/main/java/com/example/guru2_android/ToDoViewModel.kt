package com.example.guru2_android

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ToDoViewModel: ViewModel() {
    val todoList: LiveData<MutableList<Todo>>
    private val todoRepository: TodoRepository = TodoRepository.get()

    init {
        todoList = todoRepository.list()
    }

    fun getOne(id: Long) = todoRepository.getTodo(id)

    fun getByDate(toDoDate: String) = todoRepository.getByDate(toDoDate)

    fun insert(dto: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.insert(dto)
    }

    fun update(dto: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.update(dto)
    }

    fun delete(id: Long) = todoRepository.delete(id)

}