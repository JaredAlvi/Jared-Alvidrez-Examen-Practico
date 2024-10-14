package com.example.jaredalvidrezexamenpractico.presentation.task.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.room.util.copy
import com.example.jaredalvidrezexamenpractico.domain.model.Task
import com.example.jaredalvidrezexamenpractico.presentation.auth.AuthViewModel
import com.example.jaredalvidrezexamenpractico.presentation.task.FilterType
import com.example.jaredalvidrezexamenpractico.presentation.task.OrderType
import com.example.jaredalvidrezexamenpractico.presentation.task.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    taskViewModel: TaskViewModel,
    authViewModel: AuthViewModel,
    onTaskClick: (Task) -> Unit,
    onFabClick: () -> Unit,
    onLogout: () -> Unit
) {
    val tasks by taskViewModel.filteredTasks.observeAsState(emptyList())
    val selectedTasks = remember { mutableStateListOf<Task>() }
    var expandedTaskId by remember { mutableStateOf<Int?>(null) }
    var showFilterMenu by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf(FilterType.ALL) }
    var selectedOrder by remember { mutableStateOf(OrderType.BY_TITLE) } // Para manejar la ordenación seleccionada

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de tareas") },
                actions = {
                    // Botón de filtro
                    IconButton(onClick = { showFilterMenu = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Filtrar tareas")
                    }
                    // Botón para cerrar sesión
                    IconButton(onClick = {
                        authViewModel.logout()
                        onLogout()
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                    // DropdownMenu para seleccionar el filtro
                    DropdownMenu(
                        expanded = showFilterMenu,
                        onDismissRequest = { showFilterMenu = false }
                    ) {
                        // Sección de Filtros
                        DropdownMenuItem(
                            text = {
                                Row {
                                    if (selectedFilter == FilterType.ALL) {
                                        Icon(Icons.Default.Check, contentDescription = null)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Todas las tareas")
                                }
                            },
                            onClick = {
                                selectedFilter = FilterType.ALL
                                taskViewModel.setFilter(selectedFilter)
                                showFilterMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Row {
                                    if (selectedFilter == FilterType.COMPLETED) {
                                        Icon(Icons.Default.Check, contentDescription = null)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Tareas completadas")
                                }
                            },
                            onClick = {
                                selectedFilter = FilterType.COMPLETED
                                taskViewModel.setFilter(selectedFilter)
                                showFilterMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Row {
                                    if (selectedFilter == FilterType.PENDING) {
                                        Icon(Icons.Default.Check, contentDescription = null)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Tareas no completadas")
                                }
                            },
                            onClick = {
                                selectedFilter = FilterType.PENDING
                                taskViewModel.setFilter(selectedFilter)
                                showFilterMenu = false
                            }
                        )

                        // Divisor horizontal entre filtros y orden
                        HorizontalDivider()

                        // Sección de Ordenar
                        DropdownMenuItem(
                            text = {
                                Row {
                                    if (selectedOrder == OrderType.BY_TITLE) {
                                        Icon(Icons.Default.Done, contentDescription = null)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Ordenar por título")
                                }
                            },
                            onClick = {
                                selectedOrder = OrderType.BY_TITLE
                                taskViewModel.sortTasksByTitle()
                                showFilterMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Row {
                                    if (selectedOrder == OrderType.BY_COMPLETION) {
                                        Icon(Icons.Default.Done, contentDescription = null)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Ordenar por estado")
                                }
                            },
                            onClick = {
                                selectedOrder = OrderType.BY_COMPLETION
                                taskViewModel.sortTasksByCompletion()
                                showFilterMenu = false
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onFabClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(tasks) { task ->
                TaskItem(
                    taskViewModel = taskViewModel,
                    task = task,
                    selectedTasks = selectedTasks,
                    expandedTaskId = expandedTaskId,
                    onExpandChange = {
                        expandedTaskId = if (expandedTaskId == task.id) null else task.id
                    },
                    onTaskClick = { onTaskClick(task) }
                )
            }
        }
    }
}




@Composable
fun TaskItem(
    taskViewModel: TaskViewModel,
    task: Task,
    selectedTasks: MutableList<Task>,
    expandedTaskId: Int?,
    onExpandChange: () -> Unit,
    onTaskClick: () -> Unit
) {
    val isSelected = selectedTasks.contains(task)
    val isExpanded = expandedTaskId == task.id

    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        modifier = Modifier.animateContentSize()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onExpandChange() }
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    taskViewModel.updateTaskCompletion(task, !task.isCompleted)
                }) {
                    Icon(
                        Icons.Default.CheckCircle,
                        modifier = Modifier.alpha(if (task.isCompleted) 1.0f else 0.1f),
                        contentDescription = if (isSelected) "Deseleccionar" else "Seleccionar"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = task.title,
                    modifier = Modifier.alpha(if (task.isCompleted) 0.5f else 1f),
                    style = if (task.isCompleted) {
                        MaterialTheme.typography.titleMedium.copy(textDecoration = TextDecoration.LineThrough)
                    } else {
                        MaterialTheme.typography.titleMedium
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
                if (isExpanded) {
                    IconButton(onClick = { onTaskClick() }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar tarea")
                    }
                }
                if (isExpanded || isSelected) {
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = { isChecked ->
                            if (isSelected) {
                                selectedTasks.remove(task)
                            } else {
                                selectedTasks.add(task)
                            }
                        }
                    )
                }
            }
            if (isExpanded) {
                Text(
                    text = task.description ?: "Sin descripcion",
                    style =
                    if (task.isCompleted) {
                        MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.LineThrough)
                    } else {
                        MaterialTheme.typography.bodyMedium
                    },
                    modifier = Modifier.padding(
                        start = 48.dp,
                        top = 4.dp,
                    ).alpha(if (task.isCompleted) 0.5f else 1f),
                )
            }
        }
    }
}
