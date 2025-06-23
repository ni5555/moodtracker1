package com.example.tasktracker1.data.todolist

data class TodoList(
    val id: Int,
    val title: String,
    val isFavorite: Boolean = false
)
