package com.example.jaredalvidrezexamenpractico.domain.usecase

import com.example.jaredalvidrezexamenpractico.domain.model.Task
import com.example.jaredalvidrezexamenpractico.domain.repository.TaskRepository

class UpdateTaskUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: Task) {
        taskRepository.updateTask(task)
    }
}