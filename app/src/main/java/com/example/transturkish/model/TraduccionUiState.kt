package com.example.transturkish.model

data class TraduccionUiState(
    val texto: String = "",
    val traduccion: String = "",
    val turcoAEspanol: Boolean = true,
    val cargando: Boolean = false,
    val error: String? = null
)
