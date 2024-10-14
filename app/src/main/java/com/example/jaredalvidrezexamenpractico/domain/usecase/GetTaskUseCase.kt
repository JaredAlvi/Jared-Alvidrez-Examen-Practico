package com.example.jaredalvidrezexamenpractico.domain.usecase

import com.example.jaredalvidrezexamenpractico.domain.repository.TaskRepository

class GetTasksUseCase(private val taskRepository: TaskRepository) {
    operator fun invoke() = taskRepository.getAllTasks()
}