package com.example.jaredalvidrezexamenpractico.domain.repository

import androidx.lifecycle.LiveData
import com.example.jaredalvidrezexamenpractico.domain.model.Task

interface TaskRepository {
    fun getAllTasks(): LiveData<List<Task>>
    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
}