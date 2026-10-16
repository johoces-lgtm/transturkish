package com.example.transturkish.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.transturkish.ui.navigation.Screen
import com.example.transturkish.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(text = "Menu", modifier = Modifier.padding(16.dp))
                NavigationDrawerItem(
                    label = { Text(text = "Ir a Perfil") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        viewModel.navigateTo(Screen.Profile)
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Transturkish") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "¡Bienvenido a Transturkish!",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Aprende palabras en turco y español",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Practica tu vocabulario",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Próximamente: minijuego de palabras turcas",
                    style = MaterialTheme.typography.bodyMedium
                )



                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { viewModel.navigateTo(Screen.Settings) }) {
                    Text(text = "Ir a Configuracion")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        navController.navigate("registro")
                    }
                ) {
                    Text(text = "Crear mi cuenta")

                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.navigateTo(Screen.Profile)
                    }
                ) {
                    Text(text = "Mi perfil")
                }



                Spacer(modifier = Modifier.height(16.dp))


                Button(onClick = { viewModel.navigateTo(Screen.Animaciones) }) {
                    Text(text = "Guía 12 - Animaciones y Estado")
                }
                Button(onClick = { viewModel.navigateTo(Screen.Camara) }) {
                    Text(text = "Guía 13 - Cámara (Intent)")
                }
            }
        }
    }
}