package com.example.jaredalvidrezexamenpractico.presentation.task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jaredalvidrezexamenpractico.domain.model.Task
import com.example.jaredalvidrezexamenpractico.domain.usecase.AddTaskUseCase
import com.example.jaredalvidrezexamenpractico.domain.usecase.DeleteTaskUseCase
import com.example.jaredalvidrezexamenpractico.domain.usecase.GetTasksUseCase
import com.example.jaredalvidrezexamenpractico.domain.usecase.UpdateTaskUseCase
import kotlinx.coroutines.launch

enum class FilterType {
    ALL, COMPLETED, PENDING
}

enum class OrderType {
    BY_TITLE, BY_COMPLETION
}

class TaskViewModel(
    application: Application,
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : AndroidViewModel(application) {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    init {
        // Al inicializar el ViewModel, obtenemos todas las tareas
        refreshTasks()
    }

    // Función para refrescar la lista de tareas
    private fun refreshTasks() {
        // Observa la lista de tareas desde el repositorio
        getTasksUseCase().observeForever { taskList ->
            _tasks.value = taskList  // Actualiza el LiveData con las nuevas tareas
        }
    }

    // Función para insertar una nueva tarea
    fun insert(task: Task) = viewModelScope.launch {
        addTaskUseCase(task)  // Usa el caso de uso para insertar la tarea
        refreshTasks()  // Refresca la lista de tareas después de insertar
    }

    // Función para actualizar una tarea existente
    fun update(task: Task) = viewModelScope.launch {
        updateTaskUseCase(task)  // Usa el caso de uso para actualizar la tarea
        refreshTasks()  // Refresca la lista de tareas
    }

    // Función para actualizar el estado de completado de una tarea
    fun updateTaskCompletion(task: Task, isCompleted: Boolean) = viewModelScope.launch {
        val updatedTask = task.copy(isCompleted = isCompleted)
        update(updatedTask)  // Actualiza la tarea con el nuevo estado
    }

    // Función para eliminar una tarea
    fun delete(task: Task) = viewModelScope.launch {
        deleteTaskUseCase(task)  // Usa el caso de uso para eliminar la tarea
        refreshTasks()  // Refresca la lista de tareas
    }

    // Función para eliminar múltiples tareas
    fun deleteTasks(tasks: List<Task>) = viewModelScope.launch {
        tasks.forEach { delete(it) }  // Elimina cada tarea de la lista
    }
}
