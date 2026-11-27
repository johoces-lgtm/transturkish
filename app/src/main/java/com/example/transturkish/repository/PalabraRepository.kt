package com.example.transturkish.repository

import com.example.transturkish.data.model.NuevaPalabra
import com.example.transturkish.data.model.Palabra
import com.example.transturkish.data.remote.RetrofitInstance

class PalabraRepository {

    private val apiService =
        RetrofitInstance.crearServicio(
            "http://10.0.2.2:8080/"
        )

    suspend fun obtenerPalabras(): List<Palabra> {
        return apiService.obtenerPalabras()
    }

    suspend fun crearPalabra(palabra: NuevaPalabra) {
        apiService.crearPalabra(palabra)
    }

    suspend fun actualizarPalabra(
        id: Long,
        palabra: NuevaPalabra
    ) {
        apiService.actualizarPalabra(id, palabra)
    }

    suspend fun eliminarPalabra(id: Long) {
        apiService.eliminarPalabra(id)
    }
}
