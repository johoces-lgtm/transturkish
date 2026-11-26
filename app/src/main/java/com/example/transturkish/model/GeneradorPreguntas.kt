package com.example.transturkish.model

import com.example.transturkish.data.model.Palabra

fun generarPreguntas(
    palabras: List<Palabra>
): List<PreguntaJuego> {

    // Limpiar y eliminar palabras repetidas
    val vocabulario = palabras
        .map { palabra ->
            palabra.copy(
                turco = palabra.turco.trim(),
                espanol = palabra.espanol.trim()
            )
        }
        .filter {
            it.turco.isNotBlank() &&
                    it.espanol.isNotBlank()
        }
        .distinctBy {
            it.turco.lowercase()
        }

    // Obtener traducciones diferentes
    val traducciones = vocabulario
        .map { it.espanol }
        .distinctBy { it.lowercase() }

    // Necesitamos una respuesta correcta
    // y dos alternativas incorrectas
    if (traducciones.size < 3) {
        return emptyList()
    }

    // Seleccionar hasta diez palabras aleatorias
    return vocabulario
        .shuffled()
        .take(10)
        .map { palabra ->

            val incorrectas = traducciones
                .filter {
                    !it.equals(
                        palabra.espanol,
                        ignoreCase = true
                    )
                }
                .shuffled()
                .take(2)

            val alternativas =
                (incorrectas + palabra.espanol)
                    .shuffled()

            PreguntaJuego(
                palabra = palabra.turco,
                respuesta = palabra.espanol,
                alternativas = alternativas
            )
        }
}
