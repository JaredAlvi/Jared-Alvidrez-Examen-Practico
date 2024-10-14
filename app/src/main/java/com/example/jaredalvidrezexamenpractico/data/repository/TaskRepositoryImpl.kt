package com.example.jaredalvidrezexamenpractico.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.jaredalvidrezexamenpractico.data.local.db.TaskDao
import com.example.jaredalvidrezexamenpractico.data.local.model.TaskEntity
import com.example.jaredalvidrezexamenpractico.domain.model.Task
import com.example.jaredalvidrezexamenpractico.domain.model.toDomainModel
import com.example.jaredalvidrezexamenpractico.domain.model.toEntity
import com.example.jaredalvidrezexamenpractico.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val taskDao: TaskDao  // DAO para acceder a la base de datos local (Room)
) : TaskRepository {

    // Obtener todas las tareas desde Room y convertirlas al modelo de dominio
    override fun getAllTasks(): LiveData<List<Task>> {
        return taskDao.getAllTasks().map { taskEntities ->
            taskEntities.map { it.toDomainModel() }  // Convertir cada TaskEntity a Task (dominio)
        }
    }

    // Insertar una tarea en la base de datos
    override suspend fun insertTask(task: Task) {
        taskDao.insertTask(task.toEntity())  // Convertir Task a TaskEntity y guardar en Room
    }

    // Actualizar una tarea en la base de datos
    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toEntity())  // Convertir Task a TaskEntity y actualizar en Room
    }

    // Eliminar una tarea de la base de datos
    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task.toEntity())  // Convertir Task a TaskEntity y eliminar de Room
    }
}
