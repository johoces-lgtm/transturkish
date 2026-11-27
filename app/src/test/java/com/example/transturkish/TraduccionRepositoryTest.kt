package com.example.transturkish

import com.example.transturkish.data.model.DatosTraduccion
import com.example.transturkish.data.model.TraduccionResponse
import com.example.transturkish.data.remote.TraduccionApiService
import com.example.transturkish.repository.TraduccionRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

class TraduccionRepositoryTest : StringSpec({

    "El repositorio devuelve una traduccion valida" {

        val api = mockk<TraduccionApiService>()

        coEvery {
            api.traducir("Merhaba", "tr|es")
        } returns TraduccionResponse(
            DatosTraduccion("Hola"),
            200
        )

        val repository = TraduccionRepository(api)

        repository.traducir(
            "Merhaba",
            "tr|es"
        ) shouldBe "Hola"

        coVerify(exactly = 1) {
            api.traducir("Merhaba", "tr|es")
        }
    }

    "El repositorio detecta un estado incorrecto" {

        val api = mockk<TraduccionApiService>()

        coEvery {
            api.traducir("Merhaba", "tr|es")
        } returns TraduccionResponse(
            null,
            403
        )

        val repository = TraduccionRepository(api)

        shouldThrow<Exception> {
            repository.traducir(
                "Merhaba",
                "tr|es"
            )
        }.message shouldBe
                "El servicio no pudo realizar la traducción"
    }

    "El repositorio rechaza una respuesta sin datos" {

        val api = mockk<TraduccionApiService>()

        coEvery {
            api.traducir("Merhaba", "tr|es")
        } returns TraduccionResponse(
            null,
            200
        )

        val repository = TraduccionRepository(api)

        shouldThrow<Exception> {
            repository.traducir(
                "Merhaba",
                "tr|es"
            )
        }.message shouldBe
                "No se recibió una traducción válida"
    }

    "El repositorio rechaza una traduccion vacia" {

        val api = mockk<TraduccionApiService>()

        coEvery {
            api.traducir("Merhaba", "tr|es")
        } returns TraduccionResponse(
            DatosTraduccion(""),
            200
        )

        val repository = TraduccionRepository(api)

        shouldThrow<Exception> {
            repository.traducir(
                "Merhaba",
                "tr|es"
            )
        }.message shouldBe
                "No se recibió una traducción válida"
    }
})
