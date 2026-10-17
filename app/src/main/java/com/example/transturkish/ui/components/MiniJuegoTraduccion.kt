
package com.example.transturkish.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.navigation.NavController
import androidx.compose.material3.OutlinedButton

@Composable
fun MiniJuegoTraduccion(navController: NavController) {

    val palabras = listOf(
        "Merhaba",
        "Teşekkürler",
        "Su",
        "Kedi"
    )

    val alternativas = listOf(
        listOf("Hola", "Gracias", "Agua"),
        listOf("Casa", "Gracias", "Gato"),
        listOf("Libro", "Hola", "Agua"),
        listOf("Gato", "Perro", "Casa")
    )

    val respuestas = listOf(
        "Hola",
        "Gracias",
        "Agua",
        "Gato"
    )

    var preguntaActual by remember { mutableIntStateOf(0) }
    var puntaje by remember { mutableIntStateOf(0) }
    var mensaje by remember { mutableStateOf("") }
    var respondida by remember { mutableStateOf(false) }

    var respuestaCorrecta by remember {
        mutableStateOf(false)
    }

    val colorMensaje by animateColorAsState(
        targetValue = if (respuestaCorrecta) {
            Color(0xFF2E7D32)
        } else {
            MaterialTheme.colorScheme.error
        },
        label = "Color de respuesta"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


    Text(text = "Minijuego de traducción")

        Spacer(modifier = Modifier.height(12.dp))

        if (preguntaActual < palabras.size) {

            Text(
                text = "Pregunta ${preguntaActual + 1} de ${palabras.size}"
            )

            Text(text = "Puntaje: $puntaje")

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "¿Qué significa ${palabras[preguntaActual]}?"
            )

            Spacer(modifier = Modifier.height(12.dp))

            alternativas[preguntaActual].forEach { opcion ->

                Button(
                    enabled = !respondida,
                    onClick = {
                        respondida = true

                        if (opcion == respuestas[preguntaActual]) {
                            puntaje++
                            respuestaCorrecta = true
                            mensaje = "¡Correcto!"
                        } else {
                            respuestaCorrecta = false
                            mensaje = "Incorrecto. Era ${respuestas[preguntaActual]}"
                        }

                    }
                ) {
                    Text(text = opcion)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            AnimatedVisibility(visible = respondida) {
                Text(
                    text = mensaje,
                    color = colorMensaje,
                    style = MaterialTheme.typography.titleMedium
                )
            }


            if (respondida) {
                Button(
                    onClick = {
                        preguntaActual++
                        respondida = false
                        mensaje = ""
                    }
                ) {
                    Text(text = "Siguiente pregunta")
                }
            }

        } else {

            Text(text = "¡Juego terminado!")
            Text(text = "Puntaje final: $puntaje de ${palabras.size}")

            Button(
                onClick = {
                    preguntaActual = 0
                    puntaje = 0
                    mensaje = ""
                    respondida = false
                }
            ) {
                Text(text = "Volver a jugar")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text(text = "Volver al inicio")
        }
    }
}
