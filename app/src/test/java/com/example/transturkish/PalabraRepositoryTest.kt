package com.example.transturkish

import com.example.transturkish.data.model.NuevaPalabra
import com.example.transturkish.data.model.Palabra
import com.example.transturkish.data.remote.ApiService
import com.example.transturkish.repository.PalabraRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

class PalabraRepositoryTest : StringSpec({

    "El repositorio recupera las palabras de la API" {

        val api = mockk<ApiService>()

        val palabras = listOf(
            Palabra(1L, "Merhaba", "Hola"),
            Palabra(2L, "Kedi", "Gato")
        )

        coEvery {
            api.obtenerPalabras()
        } returns palabras

        val repository = PalabraRepository(api)

        repository.obtenerPalabras() shouldBe palabras

        coVerify(exactly = 1) {
            api.obtenerPalabras()
        }
    }

    "El repositorio envia una palabra nueva" {

        val api = mockk<ApiService>()

        val palabra = NuevaPalabra(
            "Elma",
            "Manzana"
        )

        coEvery {
            api.crearPalabra(palabra)
        } returns Unit

        val repository = PalabraRepository(api)

        repository.crearPalabra(palabra)

        coVerify(exactly = 1) {
            api.crearPalabra(palabra)
        }
    }

    "El repositorio actualiza la palabra correcta" {

        val api = mockk<ApiService>()

        val palabra = NuevaPalabra(
            "Kedi",
            "Gato"
        )

        coEvery {
            api.actualizarPalabra(2L, palabra)
        } returns Unit

        val repository = PalabraRepository(api)

        repository.actualizarPalabra(2L, palabra)

        coVerify(exactly = 1) {
            api.actualizarPalabra(2L, palabra)
        }
    }

    "El repositorio elimina la palabra indicada" {

        val api = mockk<ApiService>()

        coEvery {
            api.eliminarPalabra(2L)
        } returns Unit

        val repository = PalabraRepository(api)

        repository.eliminarPalabra(2L)

        coVerify(exactly = 1) {
            api.eliminarPalabra(2L)
        }
    }
})
