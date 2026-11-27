package com.example.transturkish.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SelectorModalidad(
    onServidor: () -> Unit,
    onLocal: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Encabezado principal
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),

            colors = CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.primary,

                contentColor =
                    MaterialTheme.colorScheme.onPrimary
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )

                Text(
                    text = "¡Es hora de practicar!",
                    style =
                        MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Refuerza tu vocabulario y mejora tu turco con cada partida."
                )
            }
        }

        Text(
            text = "Elige cómo practicar",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        // Modalidad conectada
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
                    Arrangement.spacedBy(10.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Wifi,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(36.dp)
                )

                Text(
                    text = "Práctica conectada",
                    style =
                        MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Vocabulario actualizado desde nuestro servidor."
                )

                Text(
                    text = "Hasta 10 preguntas",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )

                Button(
                    onClick = onServidor,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Comenzar")
                }
            }
        }

        // Modalidad sin conexión
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
                    Arrangement.spacedBy(10.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.WifiOff,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(36.dp)
                )

                Text(
                    text = "Práctica sin conexión",
                    style =
                        MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Aprende donde quieras, incluso sin Internet."
                )

                Text(
                    text = "10 preguntas",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )

                OutlinedButton(
                    onClick = onLocal,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Comenzar")
                }
            }
        }
    }
}
