package com.example.tasktracker1.data.todos

import com.example.tasktracker1.data.todos.source.LocalTodo

fun LocalTodo.toExternal() = Todo(id, todoList, title, isCompleted, date, note, dueDate, reminder)

@JvmName("LocalToExternal")
fun List<LocalTodo>.toExternal() = map(LocalTodo::toExternal)

fun Todo.toLocal() = LocalTodo(id, todoList, title, isCompleted, date, note, dueDate, reminder)

@JvmName("ExternalToLocal")
fun List<Todo>.toLocal() = map(Todo::toLocal)
