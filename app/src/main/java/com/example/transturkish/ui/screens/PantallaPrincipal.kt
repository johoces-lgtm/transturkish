package com.example.transturkish.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.transturkish.viewmodel.EstadoViewModel

@Composable
fun PantallaPrincipal(modifier: Modifier = Modifier, viewModel: EstadoViewModel = viewModel()) {
    // Observamos los estados desde el ViewModel
    val estado = viewModel.activo.collectAsState()
    val mostrarMensaje = viewModel.mostrarMensaje.collectAsState()

    // Parte 3: Simulación de carga
    if (estado.value == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        // Ya cargó, mostramos la interfaz
        val estaActivo = estado.value!!

        // Animación de color fluida
        val colorAnimado by animateColorAsState(
            targetValue = if (estaActivo) Color(0xFF4CAF50) else Color(0xFFB0BEC5),
        animationSpec = tween(durationMillis = 500),
        label = "colorAnimacion"
        )

        // Texto derivado que cambia según el estado
        val textoBoton by remember(estaActivo) {
            derivedStateOf { if (estaActivo) "Desactivar" else "Activar" }
        }

        // Parte 4: Feedback animado y botones
        Column(
            modifier = modifier
                .fillMaxSize()
        .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { viewModel.alternarEstado() },
            colors = ButtonDefaults.buttonColors(containerColor = colorAnimado),
            modifier = Modifier
                .fillMaxWidth()
            .height(60.dp)
            ) {
            Text(text = textoBoton, style = MaterialTheme.typography.titleLarge)
        }

            Spacer(modifier = Modifier.height(24.dp))

            // Animación de aparición/desaparición del mensaje
            AnimatedVisibility(visible = mostrarMensaje.value) {
                Text(
                    text = "¡Estado guardado exitosamente!",
                color = Color(0xFF4CAF50),
                style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}