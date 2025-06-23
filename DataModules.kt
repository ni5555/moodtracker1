package com.example.tasktracker1.data.di

import android.content.Context
import androidx.room.Room
import com.example.tasktracker1.data.todolist.DefaultTodoListRepository
import com.example.tasktracker1.data.todolist.TodoListRepository
import com.example.tasktracker1.data.todolist.source.TodoListDao
import com.example.tasktracker1.data.todolist.source.TodoListDatabase
import com.example.tasktracker1.data.todos.DefaultTodoRepository
import com.example.tasktracker1.data.todos.TodoRepository
import com.example.tasktracker1.data.todos.source.TodoDao
import com.example.tasktracker1.data.todos.source.TodoDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTodoListRepository(repository: DefaultTodoListRepository): TodoListRepository

    @Singleton
    @Binds
    abstract fun binTodoRepository(repository: DefaultTodoRepository): TodoRepository

}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideTodoListDatabase(@ApplicationContext context: Context): TodoListDatabase {
        return Room.databaseBuilder(
            context.applicationContext, TodoListDatabase::class.java, "todo_list.db"
        ).build()
    }

    @Provides
    fun provideTodoListDao(database: TodoListDatabase): TodoListDao = database.todoListDao()


    @Singleton
    @Provides
    fun provideTodoDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(context.applicationContext, TodoDatabase::class.java, "todo.db").build()
    }

    @Provides
    fun provideTodoDao(database: TodoDatabase): TodoDao = database.todoDao()
}