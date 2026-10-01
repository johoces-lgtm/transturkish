package com.example.transturkish.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.transturkish.ui.screens.RegistroScreen
import com.example.transturkish.ui.screens.ResumenScreen
import com.example.transturkish.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Se instancia el ViewModel una sola vez para ser compartido
    val usuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "registro"
    ) {
        composable(route = "registro") {
            RegistroScreen(navController, usuarioViewModel)
        }
        composable(route = "resumen") {
            ResumenScreen(usuarioViewModel)
        }
    }
}