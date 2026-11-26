package com.example.transturkish.data.model

data class Palabra(
    val id: Long,
    val turco: String,
    val espanol: String
)

data class NuevaPalabra(
    val turco: String,
    val espanol: String
) {
    fun esValida(): Boolean {
        return turco.isNotBlank() &&
                espanol.isNotBlank()
    }
}
