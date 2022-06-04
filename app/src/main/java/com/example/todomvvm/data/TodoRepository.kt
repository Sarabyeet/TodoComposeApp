package com.example.todomvvm.data


import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    // If the Id is null we don't want the app to crash
    suspend fun getTodo(id: Int): Todo?

    fun getTodos(): Flow<List<Todo>>
}