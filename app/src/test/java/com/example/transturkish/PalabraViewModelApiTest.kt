package com.example.transturkish

import com.example.transturkish.data.model.NuevaPalabra
import com.example.transturkish.data.model.Palabra
import com.example.transturkish.repository.PalabraRepository
import com.example.transturkish.viewmodel.PalabraViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class PalabraViewModelApiTest : StringSpec({

    beforeTest {
        Dispatchers.setMain(
            StandardTestDispatcher()
        )
    }

    afterTest {
        Dispatchers.resetMain()
    }

    "El ViewModel recupera el vocabulario" {

        runTest {

            val repository =
                mockk<PalabraRepository>()

            val palabras = listOf(
                Palabra(1L, "Merhaba", "Hola"),
                Palabra(2L, "Kedi", "Gato")
            )

            coEvery {
                repository.obtenerPalabras()
            } returns palabras

            val viewModel =
                PalabraViewModel(repository)

            viewModel.cargarPalabras()

            viewModel.estado.value.cargando shouldBe true

            advanceUntilIdle()

            val estado = viewModel.estado.value

            estado.palabras shouldBe palabras
            estado.cargando shouldBe false
            estado.consultaRealizada shouldBe true
            estado.error shouldBe null
        }
    }

    "El ViewModel informa un error de conexion" {

        runTest {

            val repository =
                mockk<PalabraRepository>()

            coEvery {
                repository.obtenerPalabras()
            } throws IOException("Sin conexion")

            val viewModel =
                PalabraViewModel(repository)

            viewModel.cargarPalabras()

            advanceUntilIdle()

            viewModel.estado.value.cargando shouldBe false

            viewModel.estado.value.error shouldBe
                    "No se pudo consultar el vocabulario"
        }
    }

    "El ViewModel guarda una palabra nueva" {

        runTest {

            val repository =
                mockk<PalabraRepository>()

            val nueva = NuevaPalabra(
                "Elma",
                "Manzana"
            )

            val palabra = Palabra(
                1L,
                "Elma",
                "Manzana"
            )

            coEvery {
                repository.crearPalabra(nueva)
            } returns Unit

            coEvery {
                repository.obtenerPalabras()
            } returns listOf(palabra)

            val viewModel =
                PalabraViewModel(repository)

            viewModel.onTurcoChange("Elma")
            viewModel.onEspanolChange("Manzana")

            viewModel.guardarPalabra()

            advanceUntilIdle()

            val estado = viewModel.estado.value

            estado.turco shouldBe ""
            estado.espanol shouldBe ""
            estado.palabras shouldBe listOf(palabra)
            estado.cargando shouldBe false

            estado.mensaje shouldBe
                    "Palabra guardada correctamente"

            coVerify(exactly = 1) {
                repository.crearPalabra(nueva)
            }
        }
    }

    "El ViewModel actualiza una palabra existente" {

        runTest {

            val repository =
                mockk<PalabraRepository>()

            val original = Palabra(
                1L,
                "Elma",
                "Manzanna"
            )

            val nueva = NuevaPalabra(
                "Elma",
                "Manzana"
            )

            val actualizada = Palabra(
                1L,
                "Elma",
                "Manzana"
            )

            coEvery {
                repository.actualizarPalabra(1L, nueva)
            } returns Unit

            coEvery {
                repository.obtenerPalabras()
            } returns listOf(actualizada)

            val viewModel =
                PalabraViewModel(repository)

            viewModel.iniciarEdicion(original)
            viewModel.onEspanolChange("Manzana")

            viewModel.guardarPalabra()

            advanceUntilIdle()

            val estado = viewModel.estado.value

            estado.editandoId shouldBe null
            estado.palabras shouldBe listOf(actualizada)

            estado.mensaje shouldBe
                    "Palabra actualizada correctamente"

            coVerify(exactly = 1) {
                repository.actualizarPalabra(1L, nueva)
            }
        }
    }

    "El ViewModel elimina una palabra" {

        runTest {

            val repository =
                mockk<PalabraRepository>()

            coEvery {
                repository.eliminarPalabra(1L)
            } returns Unit

            coEvery {
                repository.obtenerPalabras()
            } returns emptyList()

            val viewModel =
                PalabraViewModel(repository)

            viewModel.eliminarPalabra(1L)

            advanceUntilIdle()

            val estado = viewModel.estado.value

            estado.palabras.isEmpty() shouldBe true
            estado.cargando shouldBe false

            estado.mensaje shouldBe
                    "Palabra eliminada correctamente"

            coVerify(exactly = 1) {
                repository.eliminarPalabra(1L)
            }
        }
    }

    "Un error al guardar conserva los datos ingresados" {

        runTest {

            val repository =
                mockk<PalabraRepository>()

            val nueva = NuevaPalabra(
                "Elma",
                "Manzana"
            )

            coEvery {
                repository.crearPalabra(nueva)
            } throws IOException("Sin conexion")

            val viewModel =
                PalabraViewModel(repository)

            viewModel.onTurcoChange("Elma")
            viewModel.onEspanolChange("Manzana")

            viewModel.guardarPalabra()

            advanceUntilIdle()

            val estado = viewModel.estado.value

            estado.turco shouldBe "Elma"
            estado.espanol shouldBe "Manzana"
            estado.cargando shouldBe false

            (estado.error != null) shouldBe true
        }
    }
})
