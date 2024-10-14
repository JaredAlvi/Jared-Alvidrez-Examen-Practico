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
    private val _filteredTasks = MutableLiveData<List<Task>>()
    val filteredTasks: LiveData<List<Task>> = _filteredTasks

    init {
        // Observamos el LiveData de getTasksUseCase y actualizamos las listas de tareas
        getTasksUseCase().observeForever { tasks ->
            _tasks.value = tasks
            _filteredTasks.value = tasks  // Inicialmente mostrar todas las tareas
        }
    }

    fun setFilter(filter: FilterType) {
        _filteredTasks.value = when (filter) {
            FilterType.ALL -> _tasks.value
            FilterType.COMPLETED -> _tasks.value?.filter { it.isCompleted }
            FilterType.PENDING -> _tasks.value?.filter { !it.isCompleted }
        }
    }

    fun sortTasksByTitle() {
        _filteredTasks.value = _filteredTasks.value?.sortedBy { it.title }
    }

    fun sortTasksByCompletion() {
        _filteredTasks.value = _filteredTasks.value?.sortedBy { it.isCompleted }
    }

    fun insert(task: Task) = viewModelScope.launch {
        addTaskUseCase(task)
        refreshTasks()
    }

    fun update(task: Task) = viewModelScope.launch {
        updateTaskUseCase(task)
        refreshTasks()
    }

    fun updateTaskCompletion(task: Task, isCompleted: Boolean) = viewModelScope.launch {
        val updatedTask = task.copy(isCompleted = isCompleted)
        update(updatedTask)
    }

    fun delete(task: Task) = viewModelScope.launch {
        deleteTaskUseCase(task)
        refreshTasks()
    }

    fun deleteTasks(tasks: List<Task>) = viewModelScope.launch {
        tasks.forEach { delete(it) }
    }

    private fun refreshTasks() {
        getTasksUseCase().observeForever { tasks ->
            _tasks.value = tasks
            _filteredTasks.value = tasks  // Actualizar las tareas filtradas
        }
    }
}