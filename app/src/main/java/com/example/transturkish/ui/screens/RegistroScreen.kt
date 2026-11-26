package com.example.transturkish.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.transturkish.viewmodel.UsuarioViewModel

@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {

    val estado by viewModel.estado.collectAsState()

    var intentoRegistrar by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.imePadding(),
        containerColor = MaterialTheme.colorScheme.background,

        bottomBar = {

            Surface(
                color = MaterialTheme.colorScheme.surface
            ) {

                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Button(
                        onClick = {

                            intentoRegistrar = true

                            if (viewModel.validarFormulario()) {
                                navController.navigate("resumen")
                            }
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {

                        Text(
                            text = "Revisar datos",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),

            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // Regresar al login
            TextButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text("Volver al inicio de sesión")
            }

            // Encabezado del formulario
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Card(
                    modifier = Modifier.size(76.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme.primaryContainer
                    )
                ) {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Text(
                    text = "Crea tu cuenta",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Comienza tu aventura aprendiendo turco.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Campo nombre
            OutlinedTextField(
                value = estado.nombre,
                onValueChange = viewModel::onNombreChange,

                label = {
                    Text("Nombre")
                },

                leadingIcon = {
                    Icon(Icons.Default.Person, null)
                },

                singleLine = true,
                isError = estado.errores.nombre != null,

                supportingText = {
                    estado.errores.nombre?.let {
                        Text(it)
                    }
                },

                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )

            // Campo correo electrónico
            OutlinedTextField(
                value = estado.correo,
                onValueChange = viewModel::onCorreoChange,

                label = {
                    Text("Correo electrónico")
                },

                leadingIcon = {
                    Icon(Icons.Default.Email, null)
                },

                singleLine = true,
                isError = estado.errores.correo != null,

                supportingText = {
                    estado.errores.correo?.let {
                        Text(it)
                    }
                },

                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )

            // Campo contraseña
            OutlinedTextField(
                value = estado.clave,
                onValueChange = viewModel::onClaveChange,

                label = {
                    Text("Contraseña")
                },

                leadingIcon = {
                    Icon(Icons.Default.Lock, null)
                },

                visualTransformation =
                    PasswordVisualTransformation(),

                singleLine = true,
                isError = estado.errores.clave != null,

                supportingText = {
                    Text(
                        estado.errores.clave
                            ?: "Mínimo 6 caracteres"
                    )
                },

                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )

            // Campo dirección
            OutlinedTextField(
                value = estado.direccion,
                onValueChange = viewModel::onDireccionChange,

                label = {
                    Text("Dirección")
                },

                leadingIcon = {
                    Icon(Icons.Default.Home, null)
                },

                singleLine = true,
                isError = estado.errores.direccion != null,

                supportingText = {
                    estado.errores.direccion?.let {
                        Text(it)
                    }
                },

                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )

            // Términos y condiciones
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.primaryContainer
                )
            ) {

                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Checkbox(
                        checked = estado.aceptaTerminos,
                        onCheckedChange =
                            viewModel::onAceptarTerminosChange
                    )

                    Text(
                        text = "Acepto los términos y condiciones",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            if (intentoRegistrar && !estado.aceptaTerminos) {

                Text(
                    text = "Debes aceptar los términos y condiciones",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
