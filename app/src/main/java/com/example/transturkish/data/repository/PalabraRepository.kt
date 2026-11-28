package com.example.transturkish.data.repository

import com.example.transturkish.data.model.Palabra
import com.example.transturkish.data.remote.ApiService

class PalabraRepository(
    private val apiService: ApiService
) {

    suspend fun obtenerPalabras(): List<Palabra> {
        val respuesta = apiService.obtenerPalabras()

        if (respuesta.isSuccessful) {
            return respuesta.body() ?: emptyList()
        } else {
            throw Exception("Error al obtener palabras: ${respuesta.code()}")
        }
    }

    suspend fun crearPalabra(palabra: Palabra): Palabra {
        val respuesta = apiService.crearPalabra(palabra)

        if (respuesta.isSuccessful) {
            return respuesta.body()
                ?: throw Exception("El servidor no devolvió la palabra creada")
        } else {
            throw Exception("Error al crear palabra: ${respuesta.code()}")
        }
    }

    suspend fun actualizarPalabra(
        id: Long,
        palabra: Palabra
    ): Palabra {
        val respuesta = apiService.actualizarPalabra(id, palabra)

        if (respuesta.isSuccessful) {
            return respuesta.body()
                ?: throw Exception("El servidor no devolvió la palabra actualizada")
        } else {
            throw Exception("Error al actualizar palabra: ${respuesta.code()}")
        }
    }

    suspend fun eliminarPalabra(id: Long) {
        val respuesta = apiService.eliminarPalabra(id)

        if (!respuesta.isSuccessful) {
            throw Exception("Error al eliminar palabra: ${respuesta.code()}")
        }
    }
}
