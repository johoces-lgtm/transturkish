package com.example.transturkish.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.transturkish.ui.navigation.Screen
import com.example.transturkish.viewmodel.MainViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    // Navegación inferior original
    val items = listOf(Screen.Home, Screen.Profile)

    var selectedItem by remember {
        mutableIntStateOf(1)
    }

    // Guía 13: Estado de la imagen seleccionada
    var imagenUri by remember {
        mutableStateOf<Uri?>(null)
    }

    // Guía 13: Selector de imágenes de la galería
    val seleccionarImagen = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            imagenUri = uri
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            viewModel.navigateTo(screen)
                        },
                        label = {
                            Text(screen.route)
                        },
                        icon = {
                            Icon(
                                imageVector = if (screen == Screen.Home)
                                    Icons.Default.Home
                                else
                                    Icons.Default.Person,
                                contentDescription = screen.route
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Bienvenido al Perfil")

            Spacer(modifier = Modifier.height(16.dp))

            // Abre la galería del dispositivo
            Button(
                onClick = {
                    seleccionarImagen.launch("image/*")
                }
            ) {
                Text("Seleccionar imagen de galería")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Confirma que se seleccionó una imagen
            if (imagenUri != null) {
                Text("Imagen seleccionada correctamente")
            }
        }
    }
}
