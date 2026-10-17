
package com.example.transturkish.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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

    // Guarda la imagen seleccionada desde la galería
    var imagenUri by remember {
        mutableStateOf<Uri?>(null)
    }

    // Guarda la fotografía tomada con la cámara
    var fotoCamara by remember {
        mutableStateOf<Bitmap?>(null)
    }

    // Permite acceder a los recursos del celular
    val context = LocalContext.current

    // Guía 13: Seleccionar una imagen de la galería
    val seleccionarImagen = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            imagenUri = uri
            fotoCamara = null
        }
    }

    // Guía 13: Tomar una fotografía con la cámara
    val lanzarCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { imagen ->
        if (imagen != null) {
            fotoCamara = imagen
            imagenUri = null
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
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Bienvenido al Perfil")

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para abrir la galería
            Button(
                onClick = {
                    seleccionarImagen.launch("image/*")
                }
            ) {
                Text("Seleccionar imagen de galería")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para abrir la cámara
            Button(
                onClick = {
                    lanzarCamara.launch(null)
                }
            ) {
                Text("Tomar fotografía")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar fotografía tomada con la cámara
            if (fotoCamara != null) {

                Text("Fotografía tomada correctamente")

                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    bitmap = fotoCamara!!.asImageBitmap(),
                    contentDescription = "Fotografía tomada con la cámara",
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            // Mostrar imagen seleccionada de la galería
            if (imagenUri != null) {

                val bitmap = remember(imagenUri) {
                    try {
                        context.contentResolver
                            .openInputStream(imagenUri!!)
                            ?.use { BitmapFactory.decodeStream(it) }
                    } catch (e: Exception) {
                        null
                    }
                }

                if (bitmap != null) {
                    Text("Imagen seleccionada correctamente")

                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Imagen seleccionada de la galería",
                        modifier = Modifier.size(200.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text("No se pudo cargar la imagen")
                }
            }
        }
    }
}
