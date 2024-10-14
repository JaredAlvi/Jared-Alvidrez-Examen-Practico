package com.example.jaredalvidrezexamenpractico.domain.model

import com.example.jaredalvidrezexamenpractico.data.local.model.TaskEntity

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false
)

fun Task.toEntity(): TaskEntity {
    return TaskEntity(id = id, title = title, description = description, isCompleted = isCompleted)
}

fun TaskEntity.toDomainModel(): Task {
    return Task(id = id, title = title, description = description, isCompleted = isCompleted)
}