package com.example.jaredalvidrezexamenpractico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.jaredalvidrezexamenpractico.di.AppModule
import com.example.jaredalvidrezexamenpractico.presentation.task.ui.AppNavigation
import com.example.jaredalvidrezexamenpractico.ui.theme.JaredAlvidrezExamenPracticoTheme

class MainActivity : ComponentActivity() {
    private lateinit var appModule: AppModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar AppModule para inyectar dependencias
        appModule = AppModule(application)

        setContent {
            JaredAlvidrezExamenPracticoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainApp(
                        appModule = appModule,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainApp(appModule: AppModule, modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    // Navegación de la aplicación
    AppNavigation(
        taskViewModel = appModule.taskViewModel,
        authViewModel = appModule.authViewModel
    )
}
