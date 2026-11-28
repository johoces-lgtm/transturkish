package com.example.transturkish.data.remote

import com.example.transturkish.data.model.Palabra
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("api/palabras")
    suspend fun obtenerPalabras(): Response<List<Palabra>>

    @POST("api/palabras")
    suspend fun crearPalabra(
        @Body palabra: Palabra
    ): Response<Palabra>

    @PUT("api/palabras/{id}")
    suspend fun actualizarPalabra(
        @Path("id") id: Long,
        @Body palabra: Palabra
    ): Response<Palabra>

    @DELETE("api/palabras/{id}")
    suspend fun eliminarPalabra(
        @Path("id") id: Long
    ): Response<Unit>
}
