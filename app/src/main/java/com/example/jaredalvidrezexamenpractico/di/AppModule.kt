package com.example.jaredalvidrezexamenpractico.di

import android.app.Application
import com.example.jaredalvidrezexamenpractico.data.local.db.TaskDatabase
import com.example.jaredalvidrezexamenpractico.data.remote.FirebaseAuthService
import com.example.jaredalvidrezexamenpractico.data.repository.TaskRepositoryImpl
import com.example.jaredalvidrezexamenpractico.domain.repository.TaskRepository
import com.example.jaredalvidrezexamenpractico.domain.usecase.*
import com.example.jaredalvidrezexamenpractico.presentation.task.TaskViewModel
import com.example.jaredalvidrezexamenpractico.presentation.auth.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

class AppModule(application: Application) {
    private val taskDatabase = TaskDatabase.getDatabase(application)
    private val taskRepository: TaskRepository = TaskRepositoryImpl(taskDatabase.taskDao())
    private val firebaseAuthService = FirebaseAuthService(FirebaseAuth.getInstance())

    val taskViewModel = TaskViewModel(
        application,
        GetTasksUseCase(taskRepository),
        AddTaskUseCase(taskRepository),
        UpdateTaskUseCase(taskRepository),
        DeleteTaskUseCase(taskRepository)
    )

    val authViewModel = AuthViewModel(firebaseAuthService)
}
