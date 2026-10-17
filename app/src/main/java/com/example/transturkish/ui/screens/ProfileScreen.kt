
package com.example.transturkish.ui.screens

import android.content.Context
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
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.transturkish.ui.navigation.Screen
import com.example.transturkish.viewmodel.MainViewModel
import java.io.File

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

    // Guarda la dirección de la fotografía tomada
    var fotoCamaraUri by remember {
        mutableStateOf<Uri?>(null)
    }

    // Guarda la dirección preparada antes de abrir la cámara
    var uriTemporal by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current

    // Seleccionar una imagen desde la galería
    val seleccionarImagen = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            imagenUri = uri
            fotoCamaraUri = null
        }
    }

    // Tomar una fotografía y guardarla en un archivo temporal
    val lanzarCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { fotografiaGuardada ->

        if (fotografiaGuardada) {
            fotoCamaraUri = uriTemporal
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

            // Botón para seleccionar imagen de galería
            Button(
                onClick = {
                    seleccionarImagen.launch("image/*")
                }
            ) {
                Text("Seleccionar imagen de galería")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para tomar fotografía
            Button(
                onClick = {
                    try {
                        val nuevaUri = crearUriFoto(context)
                        uriTemporal = nuevaUri
                        lanzarCamara.launch(nuevaUri)
                    } catch (e: Exception) {
                        fotoCamaraUri = null
                    }
                }
            ) {
                Text("Tomar fotografía")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Elegir la imagen que se mostrará
            val uriMostrar = fotoCamaraUri ?: imagenUri

            if (uriMostrar != null) {

                // Leer la fotografía desde su Uri
                val bitmap = remember(uriMostrar) {
                    try {
                        context.contentResolver
                            .openInputStream(uriMostrar)
                            ?.use { BitmapFactory.decodeStream(it) }
                    } catch (e: Exception) {
                        null
                    }
                }

                if (bitmap != null) {

                    Text(
                        if (fotoCamaraUri != null)
                            "Fotografía tomada correctamente"
                        else
                            "Imagen seleccionada correctamente"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Imagen del perfil",
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

// Crear un archivo temporal y obtener su Uri
fun crearUriFoto(context: Context): Uri {

    // Carpeta temporal de fotografías
    val carpeta = File(context.cacheDir, "imagenes")
    carpeta.mkdirs()

    // Archivo JPG con nombre único
    val archivo = File.createTempFile(
        "foto_",
        ".jpg",
        carpeta
    )

    // Uri segura para compartir con la cámara
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        archivo
    )
}
