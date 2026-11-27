package com.example.transturkish.data.remote

import com.example.transturkish.data.model.TraduccionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TraduccionApiService {

    @GET("get")
    suspend fun traducir(
        @Query("q") texto: String,
        @Query("langpair") idiomas: String
    ): TraduccionResponse
}
