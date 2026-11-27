package com.example.transturkish.data.model

data class TraduccionResponse(
    val responseData: DatosTraduccion?,
    val responseStatus: Int
)

data class DatosTraduccion(
    val translatedText: String?
)
