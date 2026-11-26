package com.example.transturkish.data.remote

import com.example.transturkish.data.model.NuevaPalabra
import com.example.transturkish.data.model.Palabra
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("api/palabras")
    suspend fun obtenerPalabras(): List<Palabra>

    @POST("api/palabras")
    suspend fun crearPalabra(
        @Body palabra: NuevaPalabra
    ): Unit

}
