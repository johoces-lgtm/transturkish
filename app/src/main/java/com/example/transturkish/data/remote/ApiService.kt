package com.example.transturkish.data.remote

import com.example.transturkish.data.model.Palabra
import retrofit2.http.GET

interface ApiService {

    @GET("api/palabras")
    suspend fun obtenerPalabras(): List<Palabra>

}
