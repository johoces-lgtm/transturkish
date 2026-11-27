package com.example.transturkish.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.transturkish.data.model.Palabra
import com.example.transturkish.viewmodel.PalabraViewModel
import kotlinx.coroutines.launch

@Composable
fun VocabularioApi(
    viewModel: PalabraViewModel = viewModel()
) {

    val estado by
    viewModel.estado.collectAsStateWithLifecycle()

    val listaEstado = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Palabra seleccionada para confirmar su eliminación
    var palabraEliminar by remember {
        mutableStateOf<Palabra?>(null)
    }

    LaunchedEffect(Unit) {
        viewModel.cargarPalabras()
    }

    // Confirmación antes de eliminar
    palabraEliminar?.let { palabra ->

        AlertDialog(
            onDismissRequest = {
                palabraEliminar = null
            },

            title = {
                Text("¿Eliminar palabra?")
            },

            text = {
                Text(
                    "¿Quieres eliminar ${palabra.turco} " +
                            "(${palabra.espanol}) del diccionario? " +
                            "Dejará de estar disponible en las nuevas partidas conectadas."
                )
            },

            confirmButton = {

                TextButton(
                    enabled = !estado.cargando,
                    onClick = {

                        palabraEliminar = null

                        viewModel.eliminarPalabra(palabra.id)

                        scope.launch {
                            listaEstado.animateScrollToItem(0)
                        }
                    }
                ) {

                    Text(
                        text = "Eliminar",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        palabraEliminar = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    LazyColumn(
        state = listaEstado,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {

            Text(
                text = "Amplía tu vocabulario",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Aprende nuevas palabras en turco y español."
            )
        }

        // Formulario de creación y edición
        item {

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
                        text = if (estado.editandoId == null) {
                            "Agregar palabra"
                        } else {
                            "Editar palabra"
                        },

                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = estado.turco,
                        onValueChange = viewModel::onTurcoChange,

                        label = {
                            Text("Palabra en turco")
                        },

                        enabled = !estado.cargando,
                        singleLine = true,

                        isError =
                            estado.errorFormulario != null &&
                                    estado.turco.isBlank(),

                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = estado.espanol,
                        onValueChange = viewModel::onEspanolChange,

                        label = {
                            Text("Traducción al español")
                        },

                        enabled = !estado.cargando,
                        singleLine = true,

                        isError =
                            estado.errorFormulario != null &&
                                    estado.espanol.isBlank(),

                        modifier = Modifier.fillMaxWidth()
                    )

                    estado.errorFormulario?.let {

                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.guardarPalabra()
                        },

                        enabled = !estado.cargando,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            if (estado.editandoId == null) {
                                "Guardar palabra"
                            } else {
                                "Guardar cambios"
                            }
                        )
                    }

                    // Cancelar únicamente cuando estamos editando
                    AnimatedVisibility(
                        visible = estado.editandoId != null
                    ) {

                        OutlinedButton(
                            onClick = {
                                viewModel.cancelarEdicion()
                            },

                            enabled = !estado.cargando,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Cancelar edición")
                        }
                    }

                    AnimatedVisibility(
                        visible = estado.mensaje != null
                    ) {

                        Text(
                            text = estado.mensaje ?: "",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    estado.error?.let {

                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }

        item {

            OutlinedButton(
                onClick = {
                    viewModel.cargarPalabras()
                },

                enabled = !estado.cargando,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Actualizar diccionario")
            }
        }

        if (estado.cargando) {

            item {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),

                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        if (estado.consultaRealizada) {

            item {

                Text(
                    text = if (estado.error != null) {
                        "${estado.palabras.size} palabras de la última consulta"
                    } else {
                        "${estado.palabras.size} palabras disponibles"
                    },

                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            if (estado.palabras.isEmpty()) {

                item {
                    Text("Todavía no hay palabras registradas")
                }
            }
        }

        // Lista del vocabulario
        items(
            items = estado.palabras,
            key = { it.id }
        ) { palabra ->

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),

                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.primaryContainer
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement =
                            Arrangement.spacedBy(4.dp)
                    ) {

                        Text(
                            text = palabra.turco,
                            style =
                                MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = palabra.espanol,
                            style =
                                MaterialTheme.typography.bodyLarge
                        )
                    }

                    // Botón para editar
                    IconButton(
                        enabled = !estado.cargando,

                        onClick = {

                            viewModel.iniciarEdicion(palabra)

                            scope.launch {
                                listaEstado.animateScrollToItem(0)
                            }
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription =
                                "Editar ${palabra.turco}",

                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Botón para eliminar
                    IconButton(
                        enabled = !estado.cargando,

                        onClick = {
                            palabraEliminar = palabra
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription =
                                "Eliminar ${palabra.turco}",

                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}
