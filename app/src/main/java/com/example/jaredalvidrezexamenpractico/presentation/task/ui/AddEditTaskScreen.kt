package com.example.jaredalvidrezexamenpractico.presentation.task.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jaredalvidrezexamenpractico.domain.model.Task
import com.example.jaredalvidrezexamenpractico.presentation.task.TaskViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    taskViewModel: TaskViewModel,
    task: Task?,
    onSaveTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit,  // Función para eliminar la tarea
) {
    var title by remember { mutableStateOf(task?.title ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }
    var isCompleted by remember { mutableStateOf(task?.isCompleted ?: false) }

    // Variable para manejar el mensaje de error
    var titleError by remember { mutableStateOf<String?>(null) }
    val tasks by taskViewModel.filteredTasks.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (task == null) "Nueva Tarea" else "Editar Tarea") },
                actions = {
                    if (task != null) {
                        IconButton(onClick = {
                            onDeleteTask(task)
                        }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Eliminar tarea",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Validación del título antes de guardar
                when {
                    title.isBlank() -> titleError = "El título es obligatorio"
                    tasks.any { it.title == title && it.id != task?.id } -> titleError = "Ya existe una tarea con este título"
                    else -> {
                        val updatedTask = task?.copy(
                            title = title,
                            description = description,
                            isCompleted = isCompleted
                        ) ?: Task(
                            title = title,
                            description = description,
                            isCompleted = isCompleted
                        )
                        onSaveTask(updatedTask)
                    }
                }
            }) {
                Icon(Icons.Default.Check, contentDescription = "Guardar tarea")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = title,
                onValueChange = {
                    title = it
                    // Limpiar el error al escribir
                    if (titleError != null) titleError = null
                },
                label = { Text("Título") },
                singleLine = true,
                isError = titleError != null,  // Mostrar el campo en rojo si hay error
                modifier = Modifier.fillMaxWidth()
            )
            if (titleError != null) {
                Text(
                    text = titleError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción (Opcional)") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5,
                minLines = 3
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("¿Tarea completada?")
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = isCompleted,
                    onCheckedChange = { isCompleted = it }
                )
            }
        }
    }
}


