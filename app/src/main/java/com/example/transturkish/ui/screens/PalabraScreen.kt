package com.example.transturkish.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.transturkish.data.model.Palabra
import com.example.transturkish.viewmodel.PalabraViewModel
import com.example.transturkish.viewmodel.PalabraViewModelFactory

@Composable
fun PalabraScreen() {

    val viewModel: PalabraViewModel = viewModel(
        factory = PalabraViewModelFactory()
    )

    val palabras by viewModel.palabras.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.error.collectAsState()

    var turco by remember { mutableStateOf("") }
    var espanol by remember { mutableStateOf("") }
    var palabraEditando by remember { mutableStateOf<Palabra?>(null) }

    LaunchedEffect(Unit) {
        viewModel.cargarPalabras()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Diccionario Turco - Español",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = turco,
            onValueChange = { turco = it },
            label = { Text("Palabra en turco") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = espanol,
            onValueChange = { espanol = it },
            label = { Text("Traducción al español") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val editando = palabraEditando

                if (editando == null) {
                    viewModel.agregarPalabra(turco, espanol)
                } else {
                    editando.id?.let {
                        viewModel.actualizarPalabra(it, turco, espanol)
                    }
                }

                turco = ""
                espanol = ""
                palabraEditando = null
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (palabraEditando == null) "Agregar palabra"
                else "Guardar cambios"
            )
        }

        if (palabraEditando != null) {
            TextButton(
                onClick = {
                    palabraEditando = null
                    turco = ""
                    espanol = ""
                }
            ) {
                Text("Cancelar edición")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (cargando) {
            CircularProgressIndicator()
        }

        error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }

        Text(
            text = "Palabras registradas: ${palabras.size}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(palabras, key = { it.id ?: 0L }) { palabra ->

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {

                        Text(
                            text = palabra.turco,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = palabra.espanol,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Row {

                            TextButton(
                                onClick = {
                                    palabraEditando = palabra
                                    turco = palabra.turco
                                    espanol = palabra.espanol
                                }
                            ) {
                                Text("Editar")
                            }

                            TextButton(
                                onClick = {
                                    palabra.id?.let {
                                        viewModel.eliminarPalabra(it)
                                    }
                                }
                            ) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}
