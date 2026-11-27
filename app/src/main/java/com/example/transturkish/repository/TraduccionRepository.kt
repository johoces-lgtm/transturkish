package com.example.transturkish.repository

import com.example.transturkish.data.remote.TraduccionApiService
import com.example.transturkish.data.remote.TraduccionRetrofitInstance

class TraduccionRepository(
    private val api: TraduccionApiService =
        TraduccionRetrofitInstance.api
) {

    suspend fun traducir(
        texto: String,
        idiomas: String
    ): String {

        val respuesta = api.traducir(
            texto,
            idiomas
        )

        if (respuesta.responseStatus != 200) {

            throw Exception(
                "El servicio no pudo realizar la traducción"
            )
        }

        val traduccion =
            respuesta.responseData?.translatedText

        if (traduccion.isNullOrBlank()) {

            throw Exception(
                "No se recibió una traducción válida"
            )
        }

        return traduccion
    }
}
