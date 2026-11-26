package com.example.transturkish.model

// Representa una pregunta del minijuego
data class PreguntaJuego(
    val palabra: String,
    val respuesta: String,
    val alternativas: List<String>
)

// Representa el estado actual de la partida
data class JuegoUiState(
    val preguntas: List<PreguntaJuego> = emptyList(),
    val preguntaActual: Int = 0,
    val puntaje: Int = 0,
    val seleccionada: String? = null
)
