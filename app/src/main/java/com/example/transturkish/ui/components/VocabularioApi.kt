package com.example.transturkish.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.transturkish.viewmodel.PalabraViewModel

@Composable
fun VocabularioApi(
    viewModel: PalabraViewModel = viewModel()
) {

    val estado by
    viewModel.estado.collectAsStateWithLifecycle()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.primaryContainer
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "Vocabulario conectado",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Aprende palabras desde nuestro servidor",
                style = MaterialTheme.typography.bodyMedium
            )

            Button(
                onClick = {
                    viewModel.cargarPalabras()
                },
                enabled = !estado.cargando,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Consultar vocabulario")
            }

            if (estado.cargando) {

                CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally
                    )
                )

            } else if (estado.error != null) {

                Text(
                    text = estado.error ?: "",
                    color = MaterialTheme.colorScheme.error
                )

            } else if (estado.consultaRealizada) {

                Text(
                    text = "${estado.palabras.size} palabras disponibles",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                if (estado.palabras.isEmpty()) {

                    Text(
                        "Todavía no hay palabras registradas"
                    )

                } else {

                    estado.palabras.forEach { palabra ->

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = palabra.turco,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = palabra.espanol
                            )
                        }

                        HorizontalDivider()
                    }
                }
            }
        }
    }
}
