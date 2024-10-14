package com.example.jaredalvidrezexamenpractico.presentation.task.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jaredalvidrezexamenpractico.domain.model.Task
import com.example.jaredalvidrezexamenpractico.presentation.auth.AuthViewModel
import com.example.jaredalvidrezexamenpractico.presentation.auth.LoginScreen
import com.example.jaredalvidrezexamenpractico.presentation.task.TaskViewModel

@Composable
fun AppNavigation(
    taskViewModel: TaskViewModel,
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()
    val currentUser = authViewModel.getCurrentUser()

    // Si el usuario ya está autenticado, navega a la pantalla principal
    LaunchedEffect(key1 = currentUser) {
        if (currentUser != null) {
            navController.navigate("main") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (currentUser != null) "main" else "login"
    ) {
        // Pantalla de inicio de sesión
        composable("login") {
            LoginScreen(
                onLogin = { email, password ->
                    authViewModel.login(email, password, onSuccess = {
                        navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                        }
                    }, onFailure = {
                        // Manejar error de inicio de sesión
                    })
                },
                onRegister = { email, password ->
                    authViewModel.register(email, password, onSuccess = {
                        navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                        }
                    }, onFailure = {
                        // Manejar error de registro
                    })
                }
            )
        }

        // Pantalla principal (lista de tareas)
        composable("main") {
            MainScreen(
                taskViewModel = taskViewModel,
                authViewModel = authViewModel,  // Añadimos authViewModel
                onTaskClick = { task ->
                    navController.navigate("edit/${task.id}")
                },
                onFabClick = {
                    navController.navigate("add")
                },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("main") { inclusive = true }
                    }
                }
            )
        }

        // Pantalla de agregar nueva tarea
        composable("edit/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull()
            val task = taskViewModel.filteredTasks.value?.find { it.id == taskId }

            AddEditTaskScreen(
                taskViewModel,
                task = task,
                onSaveTask = { updatedTask ->
                    taskViewModel.update(updatedTask)
                    navController.popBackStack()  // Vuelve a la pantalla principal después de guardar
                },
                onDeleteTask = { taskToDelete ->
                    taskViewModel.delete(taskToDelete)
                    navController.popBackStack()  // Vuelve a la pantalla principal después de eliminar
                }
            )
        }

        composable("add") {
            AddEditTaskScreen(
                taskViewModel = taskViewModel,
                task = null,
                onSaveTask = { task ->
                    taskViewModel.insert(task)
                    navController.popBackStack() // Vuelve a la pantalla principal después de guardar
                },
                onDeleteTask = {}
            )
        }
    }
}