package com.example.transturkish.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.transturkish.viewmodel.TraduccionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TraductorScreen(
    navController: NavController,
    viewModel: TraduccionViewModel = viewModel()
) {

    val estado by
    viewModel.estado.collectAsStateWithLifecycle()

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text(
                        text = "Traductor",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),

            verticalArrangement =
                Arrangement.spacedBy(20.dp)
        ) {

            // Encabezado
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
                        Arrangement.spacedBy(10.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.Translate,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )

                    Text(
                        text = "Conecta con otro idioma",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "Traduce palabras y frases al instante."
                    )
                }
            }

            // Selector de idiomas
            Row(
                modifier = Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Card(
                    modifier = Modifier.weight(1f),

                    colors = CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme.primaryContainer
                    )
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),

                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = if (estado.turcoAEspanol) {
                                "Turco"
                            } else {
                                "Español"
                            },

                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                IconButton(
                    onClick = {
                        viewModel.invertirIdiomas()
                    },

                    enabled = !estado.cargando
                ) {

                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = "Invertir idiomas",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Card(
                    modifier = Modifier.weight(1f),

                    colors = CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme.primaryContainer
                    )
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),

                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = if (estado.turcoAEspanol) {
                                "Español"
                            } else {
                                "Turco"
                            },

                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Texto original
            OutlinedTextField(
                value = estado.texto,
                onValueChange = viewModel::onTextoChange,

                label = {
                    Text("Texto original")
                },

                placeholder = {
                    Text("Escribe una palabra o frase")
                },

                enabled = !estado.cargando,
                minLines = 4,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "${estado.texto.toByteArray(Charsets.UTF_8).size}/500 bytes",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Button(
                onClick = {
                    viewModel.traducir()
                },

                enabled = !estado.cargando,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {

                Text("Traducir")
            }

            OutlinedButton(
                onClick = {
                    viewModel.limpiar()
                },

                enabled = !estado.cargando,

                modifier = Modifier.fillMaxWidth()
            ) {

                Text("Limpiar")
            }

            if (estado.cargando) {

                Box(
                    modifier = Modifier.fillMaxWidth(),

                    contentAlignment = Alignment.Center
                ) {

                    CircularProgressIndicator()
                }
            }

            estado.error?.let {

                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }

            // Resultado animado
            AnimatedVisibility(
                visible = estado.traduccion.isNotBlank()
            ) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),

                    colors = CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme.primaryContainer
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(24.dp),

                        verticalArrangement =
                            Arrangement.spacedBy(12.dp)
                    ) {

                        Text(
                            text = "TRADUCCIÓN",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = estado.traduccion,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Text(
                text = "Traducciones proporcionadas por MyMemory. No ingreses información personal o confidencial.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
