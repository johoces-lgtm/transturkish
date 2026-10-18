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
import com.example.transturkish.ui.screens.PantallaPrincipal
import com.example.transturkish.ui.screens.CamaraScreen
import com.example.transturkish.ui.theme.Guia9Theme
import com.example.transturkish.viewmodel.MainViewModel
import com.example.transturkish.viewmodel.UsuarioViewModel
import com.example.transturkish.viewmodel.ImagenViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Guia9Theme {

                // ViewModel para la navegación
                val viewModel: MainViewModel = viewModel()

                // ViewModel compartido entre Registro y Resumen
                val usuarioViewModel: UsuarioViewModel = viewModel()

                // ViewModel para conservar la imagen al navegar
                val imagenViewModel: ImagenViewModel = viewModel()

                val navController = rememberNavController()

                // Escuchar los eventos de navegación
                LaunchedEffect(key1 = Unit) {
                    viewModel.navigationEvents.collectLatest { event ->
                        when (event) {

                            is NavigationEvent.NavigateTo -> {
                                navController.navigate(
                                    route = event.route.route
                                ) {
                                    event.popUpToRoute?.let {
                                        popUpTo(route = it.route) {
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

                        NavHost(
                            navController = navController,
                            startDestination = Screen.Home.route
                        ) {

                            // Pantalla Inicio
                            composable(route = Screen.Home.route) {
                                HomeScreen(
                                    navController = navController,
                                    viewModel = viewModel
                                )
                            }

                            // Pantalla Perfil
                            composable(route = Screen.Profile.route) {
                                ProfileScreen(
                                    navController = navController,
                                    viewModel = viewModel,
                                    imagenViewModel = imagenViewModel
                                )
                            }

                            // Pantalla Configuración
                            composable(route = Screen.Settings.route) {
                                SettingsScreen(
                                    navController = navController,
                                    viewModel = viewModel
                                )
                            }

                            // Pantalla Registro
                            composable(route = "registro") {
                                RegistroScreen(
                                    navController = navController,
                                    viewModel = usuarioViewModel
                                )
                            }

                            // Pantalla Resumen
                            composable(route = "resumen") {
                                ResumenScreen(
                                    viewModel = usuarioViewModel
                                )
                            }

                            // Pantalla Animaciones - Guía 12
                            composable(route = Screen.Animaciones.route) {
                                PantallaPrincipal()
                            }

                            // Pantalla Cámara - Guía 13
                            composable(route = Screen.Camara.route) {
                                CamaraScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
