package com.example.todomvvm.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    // If the Id is null we don't want the app to crash
    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getTodo(id: Int): Todo?

    @Query("SELECT * FROM todo")
    fun getTodos(): Flow<List<Todo>>
}