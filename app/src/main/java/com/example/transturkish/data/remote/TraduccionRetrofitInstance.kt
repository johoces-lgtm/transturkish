package com.example.transturkish.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TraduccionRetrofitInstance {

    val api: TraduccionApiService =
        Retrofit.Builder()
            .baseUrl(
                "https://api.mymemory.translated.net/"
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(
                TraduccionApiService::class.java
            )
}
