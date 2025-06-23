package com.example.tasktracker1.ui.theme.home
import com.example.tasktracker1.data.todolist.TodoList


data class HomeUiState(
    val todoLists: List<TodoList> = emptyList()
)