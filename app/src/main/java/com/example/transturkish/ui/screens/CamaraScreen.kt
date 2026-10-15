package com.example.transturkish.ui.screens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun CamaraScreen(modifier: Modifier = Modifier) {
    // Estado que guarda la foto capturada en miniatura[cite: 15, 17]
    var foto by remember { mutableStateOf<Bitmap?>(null) }

    // Lanzador que abre la cámara del sistema[cite: 15]
    val lanzarCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { imagen: Bitmap? ->
        foto = imagen
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (foto == null) {
            Text(text = "Aún no has tomado una foto", style = MaterialTheme.typography.bodyLarge)
        } else {
            Image(
                bitmap = foto!!.asImageBitmap(),
            contentDescription = "Foto capturada",
            modifier = Modifier.fillMaxWidth().weight(1f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Reto 1: Mostrar el tamaño de la foto[cite: 18]
            Text(text = "Tamaño: ${foto?.width} x ${foto?.height} píxeles")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { lanzarCamara.launch(null) },
        enabled = foto == null // Reto 3: Deshabilita si ya hay foto
        ) {
        Text("Tomar foto")
    }

        // Reto 2: Botón para eliminar foto
        if (foto != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { foto = null }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                Text("Eliminar foto")
            }
        }
    }
}