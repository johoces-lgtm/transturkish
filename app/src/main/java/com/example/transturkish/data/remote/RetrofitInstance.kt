package com.example.transturkish.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    fun crearServicio(urlBase: String): ApiService {

        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(ApiService::class.java)
    }
}
