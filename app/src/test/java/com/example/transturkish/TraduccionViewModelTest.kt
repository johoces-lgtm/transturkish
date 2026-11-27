package com.example.transturkish

import com.example.transturkish.repository.TraduccionRepository
import com.example.transturkish.viewmodel.TraduccionViewModel
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
class TraduccionViewModelTest : StringSpec({

    beforeTest {
        Dispatchers.setMain(
            StandardTestDispatcher()
        )
    }

    afterTest {
        Dispatchers.resetMain()
    }

    "El texto vacio genera un error de validacion" {

        val repository =
            mockk<TraduccionRepository>()

        val viewModel =
            TraduccionViewModel(repository)

        viewModel.traducir()

        viewModel.estado.value.error shouldBe
                "Ingresa un texto para traducir"

        coVerify(exactly = 0) {
            repository.traducir(any(), any())
        }
    }

    "El texto no puede superar los 500 bytes" {

        val repository =
            mockk<TraduccionRepository>()

        val viewModel =
            TraduccionViewModel(repository)

        viewModel.onTextoChange(
            "ş".repeat(251)
        )

        viewModel.traducir()

        viewModel.estado.value.error shouldBe
                "El texto supera el límite de 500 bytes"

        coVerify(exactly = 0) {
            repository.traducir(any(), any())
        }
    }

    "El ViewModel traduce del turco al espanol" {

        runTest {

            val repository =
                mockk<TraduccionRepository>()

            coEvery {
                repository.traducir(
                    "Merhaba",
                    "tr|es"
                )
            } returns "Hola"

            val viewModel =
                TraduccionViewModel(repository)

            viewModel.onTextoChange("Merhaba")

            viewModel.traducir()

            viewModel.estado.value.cargando shouldBe true

            advanceUntilIdle()

            val estado = viewModel.estado.value

            estado.traduccion shouldBe "Hola"
            estado.cargando shouldBe false
            estado.error shouldBe null
        }
    }

    "Invertir idiomas utiliza la traduccion anterior" {

        runTest {

            val repository =
                mockk<TraduccionRepository>()

            coEvery {
                repository.traducir(
                    "Merhaba",
                    "tr|es"
                )
            } returns "Hola"

            val viewModel =
                TraduccionViewModel(repository)

            viewModel.onTextoChange("Merhaba")
            viewModel.traducir()

            advanceUntilIdle()

            viewModel.invertirIdiomas()

            val estado = viewModel.estado.value

            estado.turcoAEspanol shouldBe false
            estado.texto shouldBe "Hola"
            estado.traduccion shouldBe ""
        }
    }

    "El ViewModel traduce del espanol al turco" {

        runTest {

            val repository =
                mockk<TraduccionRepository>()

            coEvery {
                repository.traducir(
                    "Hola",
                    "es|tr"
                )
            } returns "Merhaba"

            val viewModel =
                TraduccionViewModel(repository)

            viewModel.invertirIdiomas()
            viewModel.onTextoChange("Hola")
            viewModel.traducir()

            advanceUntilIdle()

            viewModel.estado.value.traduccion shouldBe
                    "Merhaba"

            coVerify(exactly = 1) {
                repository.traducir(
                    "Hola",
                    "es|tr"
                )
            }
        }
    }

    "Un error de conexion se informa al usuario" {

        runTest {

            val repository =
                mockk<TraduccionRepository>()

            coEvery {
                repository.traducir(
                    "Merhaba",
                    "tr|es"
                )
            } throws IOException("Sin conexion")

            val viewModel =
                TraduccionViewModel(repository)

            viewModel.onTextoChange("Merhaba")
            viewModel.traducir()

            advanceUntilIdle()

            val estado = viewModel.estado.value

            estado.cargando shouldBe false
            estado.texto shouldBe "Merhaba"
            estado.traduccion shouldBe ""

            (estado.error != null) shouldBe true
        }
    }

    "Limpiar elimina el texto y los errores" {

        val repository =
            mockk<TraduccionRepository>()

        val viewModel =
            TraduccionViewModel(repository)

        viewModel.traducir()

        viewModel.onTextoChange("Merhaba")

        viewModel.limpiar()

        val estado = viewModel.estado.value

        estado.texto shouldBe ""
        estado.traduccion shouldBe ""
        estado.error shouldBe null
    }

    "Se evitan cambios y consultas mientras carga" {

        runTest {

            val repository =
                mockk<TraduccionRepository>()

            coEvery {
                repository.traducir(
                    "Merhaba",
                    "tr|es"
                )
            } returns "Hola"

            val viewModel =
                TraduccionViewModel(repository)

            viewModel.onTextoChange("Merhaba")

            viewModel.traducir()
            viewModel.traducir()

            viewModel.onTextoChange("Kedi")
            viewModel.invertirIdiomas()
            viewModel.limpiar()

            advanceUntilIdle()

            val estado = viewModel.estado.value

            estado.texto shouldBe "Merhaba"
            estado.turcoAEspanol shouldBe true
            estado.traduccion shouldBe "Hola"

            coVerify(exactly = 1) {
                repository.traducir(
                    "Merhaba",
                    "tr|es"
                )
            }
        }
    }
})
