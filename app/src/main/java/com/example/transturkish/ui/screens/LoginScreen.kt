package com.example.transturkish.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.transturkish.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {

    val estado by viewModel.estado.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        // Identidad visual
        Card(
            modifier = Modifier.size(88.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "TR",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Text(
            text = "TransTurkish",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Aprende. Practica. Progresa.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bienvenida
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "¡Bienvenido!",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Inicia sesión para continuar aprendiendo.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }

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

            isError = estado.errorCorreo != null,

            supportingText = {
                estado.errorCorreo?.let {
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

            visualTransformation = PasswordVisualTransformation(),

            singleLine = true,

            isError = estado.errorClave != null,

            supportingText = {
                estado.errorClave?.let {
                    Text(it)
                }
            },

            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        )

        // Botón de inicio de sesión
        Button(
            onClick = {
                viewModel.intentarInicioSesion()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Iniciar sesión",
                fontWeight = FontWeight.Bold
            )
        }

        // Mensaje temporal hasta conectar Spring Boot
        estado.mensaje?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "¿Todavía no tienes cuenta?",
            style = MaterialTheme.typography.bodyMedium
        )

        OutlinedButton(
            onClick = {
                navController.navigate("registro")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Crear mi cuenta")
        }
    }
}
