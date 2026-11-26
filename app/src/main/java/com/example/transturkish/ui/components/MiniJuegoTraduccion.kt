package com.example.transturkish.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.transturkish.viewmodel.JuegoViewModel

@Composable
fun MiniJuegoTraduccion(
    navController: NavController,
    viewModel: JuegoViewModel = viewModel()
) {

    val estado by viewModel.estado.collectAsState()

    val verdeCorrecto = Color(0xFF2E7D32)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),

        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = "Practica vocabulario",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        if (estado.preguntas.isEmpty()) {

            Text("No hay preguntas disponibles")

        } else if (
            estado.preguntaActual < estado.preguntas.size
        ) {

            val pregunta = estado.preguntas[
                estado.preguntaActual
            ]

            val respondida = estado.seleccionada != null

            val esCorrecta =
                estado.seleccionada == pregunta.respuesta

            // Encabezado con pregunta y puntaje
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Pregunta ${estado.preguntaActual + 1} de ${estado.preguntas.size}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "${estado.puntaje} puntos",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            // Barra de progreso
            val respondidas = estado.preguntaActual +
                    if (respondida) 1 else 0

            LinearProgressIndicator(
                progress = {
                    respondidas.toFloat() /
                            estado.preguntas.size
                },

                modifier = Modifier.fillMaxWidth(),

                color = MaterialTheme.colorScheme.primary,

                trackColor =
                    MaterialTheme.colorScheme.primaryContainer
            )

            // Tarjeta con palabra en turco
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),

                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.primaryContainer
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(28.dp),

                    horizontalAlignment =
                        Alignment.CenterHorizontally,

                    verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "¿Qué significa esta palabra?",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = pregunta.palabra,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Turco → Español",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Alternativas de respuesta
            pregunta.alternativas.forEach { opcion ->

                val colorAlternativa =
                    if (respondida && opcion == pregunta.respuesta) {
                        verdeCorrecto

                    } else if (
                        respondida &&
                        opcion == estado.seleccionada
                    ) {
                        MaterialTheme.colorScheme.error

                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    }

                val colorTexto =
                    if (respondida && opcion == pregunta.respuesta) {
                        Color.White

                    } else if (
                        respondida &&
                        opcion == estado.seleccionada
                    ) {
                        MaterialTheme.colorScheme.onError

                    } else {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    }

                val colorAnimado by animateColorAsState(
                    targetValue = colorAlternativa,
                    label = "Color de alternativa"
                )

                Button(
                    onClick = {
                        viewModel.responder(opcion)
                    },

                    enabled = !respondida,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),

                    shape = RoundedCornerShape(16.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorAnimado,
                        contentColor = colorTexto,
                        disabledContainerColor = colorAnimado,
                        disabledContentColor = colorTexto
                    )
                ) {

                    Text(
                        text = opcion,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            // Retroalimentación animada
            AnimatedVisibility(visible = respondida) {

                if (respondida) {

                    Column(
                        verticalArrangement =
                            Arrangement.spacedBy(6.dp)
                    ) {

                        Text(
                            text = if (esCorrecta) {
                                "¡Correcto!"
                            } else {
                                "¡Sigue practicando!"
                            },

                            color = if (esCorrecta) {
                                verdeCorrecto
                            } else {
                                MaterialTheme.colorScheme.error
                            },

                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = "${pregunta.palabra} significa ${pregunta.respuesta}.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            // Avanzar después de responder
            if (respondida) {

                Button(
                    onClick = {
                        viewModel.siguientePregunta()
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {

                    Text(
                        text = if (
                            estado.preguntaActual ==
                            estado.preguntas.size - 1
                        ) {
                            "Ver resultados"
                        } else {
                            "Siguiente pregunta"
                        }
                    )
                }
            }

        } else {

            // Resultado final de la partida
            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(24.dp),

                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.primaryContainer
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(28.dp),

                    horizontalAlignment =
                        Alignment.CenterHorizontally,

                    verticalArrangement =
                        Arrangement.spacedBy(16.dp)
                ) {

                    Text(
                        text = "¡Partida completada!",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${estado.puntaje}/${estado.preguntas.size}",
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Respuestas correctas"
                    )

                    val porcentaje =
                        estado.puntaje * 100 /
                                estado.preguntas.size

                    Text(
                        text = "$porcentaje% de aciertos",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = if (
                            estado.puntaje == estado.preguntas.size
                        ) {
                            "¡Excelente trabajo!"
                        } else if (
                            porcentaje >= 50
                        ) {
                            "¡Vas por buen camino!"
                        } else {
                            "Sigue practicando, puedes mejorar."
                        }
                    )
                }
            }

            Button(
                onClick = {
                    viewModel.reiniciarJuego()
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {

                Text("Volver a jugar")
            }
        }

        OutlinedButton(
            onClick = {
                navController.popBackStack()
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {

            Text("Volver al inicio")
        }
    }
}
