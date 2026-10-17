
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
import com.example.transturkish.ui.screens.SettingsScreen
import com.example.transturkish.ui.screens.RegistroScreen
import com.example.transturkish.ui.screens.ResumenScreen
import com.example.transturkish.ui.screens.PantallaPrincipal
import com.example.transturkish.ui.screens.CamaraScreen
import com.example.transturkish.ui.theme.Guia9Theme
import com.example.transturkish.viewmodel.MainViewModel
import com.example.transturkish.viewmodel.UsuarioViewModel
import kotlinx.coroutines.flow.collectLatest
import com.example.transturkish.ui.components.MiniJuegoTraduccion

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            Guia9Theme {

                // ViewModel para manejar la navegación
                val viewModel: MainViewModel = viewModel()

                // ViewModel compartido para el formulario
                val usuarioViewModel: UsuarioViewModel = viewModel()

                // Controlador de navegación
                val navController = rememberNavController()

                // Escuchamos los eventos de navegación
                LaunchedEffect(Unit) {

                    viewModel.navigationEvents.collectLatest { event ->

                        when (event) {

                            is NavigationEvent.NavigateTo -> {

                                navController.navigate(event.route.route) {

                                    event.popUpToRoute?.let {
                                        popUpTo(it.route) {
                                            inclusive = event.inclusive
                                        }
                                    }

                                    launchSingleTop = event.singleTop
                                    restoreState = true
                                }
                            }

                            is NavigationEvent.PopBackStack -> {
                                navController.popBackStack()
                            }

                            is NavigationEvent.NavigateUp -> {
                                navController.navigateUp()
                            }
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        // Todas las rutas deben estar
                        // dentro de este NavHost
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Home.route
                        ) {

                            // Pantalla de inicio
                            composable(Screen.Home.route) {
                                HomeScreen(
                                    navController = navController,
                                    viewModel = viewModel
                                )
                            }

                            // Pantalla de minijuego
                            composable("minijuego") {
                                MiniJuegoTraduccion(navController = navController)
                            }


                            // Pantalla de perfil
                            composable(Screen.Profile.route) {
                                ProfileScreen(
                                    navController = navController,
                                    viewModel = viewModel
                                )
                            }

                            // Pantalla de configuración
                            composable(Screen.Settings.route) {
                                SettingsScreen(
                                    navController = navController,
                                    viewModel = viewModel
                                )
                            }

                            // Formulario de registro
                            composable("registro") {
                                RegistroScreen(
                                    navController = navController,
                                    viewModel = usuarioViewModel
                                )
                            }

                            // Resumen del formulario
                            composable("resumen") {
                                ResumenScreen(
                                    viewModel = usuarioViewModel
                                )
                            }

                            // Guía 12: Animaciones y estados
                            composable(Screen.Animaciones.route) {
                                PantallaPrincipal()
                            }

                            // Guía 13: Cámara
                            composable(Screen.Camara.route) {
                                CamaraScreen()
                            }

                        }
                    }
                }
            }
        }
    }
}
