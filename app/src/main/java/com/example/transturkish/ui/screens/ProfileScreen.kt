package com.example.transturkish.ui.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.transturkish.ui.components.ImagenInteligente
import com.example.transturkish.ui.navigation.Screen
import com.example.transturkish.viewmodel.ImagenViewModel
import com.example.transturkish.viewmodel.MainViewModel
import java.io.File

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel(),
    imagenViewModel: ImagenViewModel = viewModel()
) {
    // Navegación inferior original
    val items = listOf(Screen.Home, Screen.Profile)

    var selectedItem by remember {
        mutableIntStateOf(1)
    }

    // Guía 13: Observar la imagen almacenada en ImagenViewModel
    val imagenUri by imagenViewModel.imagenUri.collectAsStateWithLifecycle()

    // Dirección temporal donde la cámara guardará la fotografía
    var uriTemporal by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current

    // Guía 13: Seleccionar una imagen desde la galería
    val seleccionarImagen = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            imagenViewModel.actualizarImagen(uri)
        }
    }

    // Guía 13: Tomar una fotografía con TakePicture()
    val lanzarCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { fotografiaGuardada ->
        if (fotografiaGuardada) {
            uriTemporal?.let { uri ->
                imagenViewModel.actualizarImagen(uri)
            }
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
                                imageVector = if (screen == Screen.Home) {
                                    Icons.Default.Home
                                } else {
                                    Icons.Default.Person
                                },
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
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text("Bienvenido al Perfil")

            Spacer(modifier = Modifier.height(16.dp))

            // Guía 13: Mostrar fotografía circular o ícono predeterminado
            ImagenInteligente(
                imagenUri = imagenUri
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Guía 12: Mostrar mensaje con animación
            AnimatedVisibility(
                visible = imagenUri != null
            ) {
                Text(
                    text = "¡Imagen de perfil cargada correctamente!",
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para seleccionar una imagen desde la galería
            Button(
                onClick = {
                    seleccionarImagen.launch("image/*")
                }
            ) {
                Text("Seleccionar imagen de galería")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para tomar una fotografía
            Button(
                onClick = {
                    try {
                        val nuevaUri = crearUriFoto(context)
                        uriTemporal = nuevaUri
                        lanzarCamara.launch(nuevaUri)
                    } catch (e: Exception) {
                        // Conservamos la imagen anterior
                    }
                }
            ) {
                Text("Tomar fotografía")
            }
        }
    }
}

// Guía 13: Crear archivo temporal para la cámara
fun crearUriFoto(context: Context): Uri {

    val carpeta = File(context.cacheDir, "imagenes")
    carpeta.mkdirs()

    val archivo = File.createTempFile(
        "foto_",
        ".jpg",
        carpeta
    )

    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        archivo
    )
}
