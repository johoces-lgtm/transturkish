package com.example.transturkish.data.remote

import com.example.transturkish.data.model.NuevaPalabra
import com.example.transturkish.data.model.Palabra
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path

interface ApiService {

    @GET("api/palabras")
    suspend fun obtenerPalabras(): List<Palabra>

    @POST("api/palabras")
    suspend fun crearPalabra(
        @Body palabra: NuevaPalabra
    ): Unit

    @PUT("api/palabras/{id}")
    suspend fun actualizarPalabra(
        @Path("id") id: Long,
        @Body palabra: NuevaPalabra
    ): Unit

    @DELETE("api/palabras/{id}")
    suspend fun eliminarPalabra(
        @Path("id") id: Long
    ): Unit
}
