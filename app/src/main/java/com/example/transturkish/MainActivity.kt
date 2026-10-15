package com.example.transturkish

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.transturkish.ui.navigation.NavigationEvent
import com.example.transturkish.ui.navigation.Screen
import com.example.transturkish.ui.screens.HomeScreen
import com.example.transturkish.ui.screens.ProfileScreen
import com.example.transturkish.ui.screens.RegistroScreen
import com.example.transturkish.ui.screens.ResumenScreen
import com.example.transturkish.ui.screens.SettingsScreen
import com.example.transturkish.ui.theme.Guia9Theme
import com.example.transturkish.viewmodel.MainViewModel
import com.example.transturkish.viewmodel.UsuarioViewModel
import kotlinx.coroutines.flow.collectLatest

// Asegúrate de importar tus nuevas pantallas y ViewModel
// import com.tu_paquete.transturkish.ui.screen.RegistroScreen
// import com.tu_paquete.transturkish.ui.screen.ResumenScreen
// import com.tu_paquete.transturkish.viewmodel.UsuarioViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Guia9Theme {
                // Tu ViewModel actual para la navegación
                val viewModel: MainViewModel = viewModel()

                // Se Instancia el ViewModel de la guía una sola vez para ser compartido
                val usuarioViewModel: UsuarioViewModel = viewModel()

                val navController = rememberNavController()

                // Escucha eventos de navegación emitidos por el viewModel (tu código original)
                LaunchedEffect(key1 = Unit) {
                    viewModel.navigationEvents.collectLatest { event ->
                        when (event) {
                            is NavigationEvent.NavigateTo -> {
                                navController.navigate(route = event.route.route) {
                                    event.popUpToRoute?.let {
                                        popUpTo(route = it.route) {
                                            inclusive = event.inclusive
                                        }
                                    }
                                    launchSingleTop = event.singleTop
                                    restoreState = true
                                }
                            }
                            is NavigationEvent.PopBackStack -> navController.popBackStack()
                            is NavigationEvent.NavigateUp -> navController.navigateUp()
                        }
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Agregamos el Box para aplicar el padding interno que requiere Scaffold
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Home.route // Aquí puedes poner "registro" si quieres que sea la pantalla inicial
                        ) {
                            // --- Tus pantallas originales ---
                            composable(route = Screen.Home.route) {
                                HomeScreen(navController = navController, viewModel = viewModel)
                            }
                            composable(route = Screen.Profile.route) {
                                ProfileScreen(navController = navController, viewModel = viewModel)
                            }
                            composable(route = Screen.Settings.route) {
                                SettingsScreen(navController = navController, viewModel = viewModel)
                            }

                            // --- Las nuevas pantallas de la guía ---
                            composable(route = "registro") {
                                RegistroScreen(navController = navController, viewModel = usuarioViewModel)
                            }
                            composable(route = "resumen") {
                                ResumenScreen(viewModel = usuarioViewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}