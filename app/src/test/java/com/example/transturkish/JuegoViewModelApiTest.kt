package com.example.transturkish

import com.example.transturkish.data.model.Palabra
import com.example.transturkish.repository.PalabraRepository
import com.example.transturkish.viewmodel.JuegoViewModel
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
class JuegoViewModelApiTest : StringSpec({

    val vocabulario = listOf(
        Palabra(1L, "Merhaba", "Hola"),
        Palabra(2L, "Kedi", "Gato"),
        Palabra(3L, "Su", "Agua"),
        Palabra(4L, "Kitap", "Libro"),
        Palabra(5L, "Ev", "Casa"),
        Palabra(6L, "Köpek", "Perro"),
        Palabra(7L, "Okul", "Escuela"),
        Palabra(8L, "Arkadaş", "Amigo"),
        Palabra(9L, "Günaydın", "Buenos días"),
        Palabra(10L, "Teşekkürler", "Gracias")
    )

    beforeTest {
        Dispatchers.setMain(
            StandardTestDispatcher()
        )
    }

    afterTest {
        Dispatchers.resetMain()
    }

    "El servidor genera una partida de diez preguntas" {

        runTest {

            val repository =
                mockk<PalabraRepository>()

            coEvery {
                repository.obtenerPalabras()
            } returns vocabulario

            val viewModel =
                JuegoViewModel(repository)

            viewModel.cargarPartidaServidor()

            viewModel.estado.value.cargando shouldBe true

            advanceUntilIdle()

            val estado = viewModel.estado.value

            estado.preguntas.size shouldBe 10
            estado.origenServidor shouldBe true
            estado.cargando shouldBe false
            estado.errorServidor shouldBe null
            estado.puntaje shouldBe 0
        }
    }

    "El servidor con pocas palabras no genera una partida" {

        runTest {

            val repository =
                mockk<PalabraRepository>()

            coEvery {
                repository.obtenerPalabras()
            } returns vocabulario.take(2)

            val viewModel =
                JuegoViewModel(repository)

            viewModel.cargarPartidaServidor()

            advanceUntilIdle()

            val estado = viewModel.estado.value

            estado.origenServidor shouldBe false
            estado.cargando shouldBe false

            estado.errorServidor shouldBe
                    "Se necesitan al menos tres traducciones diferentes"

            estado.preguntas.size shouldBe 10
        }
    }

    "Un error de conexion conserva la partida local" {

        runTest {

            val repository =
                mockk<PalabraRepository>()

            coEvery {
                repository.obtenerPalabras()
            } throws IOException("Sin conexion")

            val viewModel =
                JuegoViewModel(repository)

            val preguntasOriginales =
                viewModel.estado.value.preguntas

            viewModel.cargarPartidaServidor()

            advanceUntilIdle()

            val estado = viewModel.estado.value

            estado.cargando shouldBe false
            estado.origenServidor shouldBe false

            estado.preguntas shouldBe preguntasOriginales

            estado.errorServidor shouldBe
                    "No se pudo conectar con el servidor. Puedes practicar sin conexión."
        }
    }

    "Reiniciar una partida conserva el origen del servidor" {

        runTest {

            val repository =
                mockk<PalabraRepository>()

            coEvery {
                repository.obtenerPalabras()
            } returns vocabulario

            val viewModel =
                JuegoViewModel(repository)

            viewModel.cargarPartidaServidor()

            advanceUntilIdle()

            val pregunta =
                viewModel.estado.value.preguntas.first()

            viewModel.responder(pregunta.respuesta)

            viewModel.siguientePregunta()

            viewModel.reiniciarJuego()

            val estado = viewModel.estado.value

            estado.origenServidor shouldBe true
            estado.preguntas.size shouldBe 10
            estado.puntaje shouldBe 0
            estado.preguntaActual shouldBe 0
            estado.seleccionada shouldBe null

            coVerify(exactly = 1) {
                repository.obtenerPalabras()
            }
        }
    }

    "Es posible regresar a la modalidad sin conexion" {

        runTest {

            val repository =
                mockk<PalabraRepository>()

            coEvery {
                repository.obtenerPalabras()
            } returns vocabulario

            val viewModel =
                JuegoViewModel(repository)

            viewModel.cargarPartidaServidor()

            advanceUntilIdle()

            viewModel.iniciarPartidaLocal()

            val estado = viewModel.estado.value

            estado.origenServidor shouldBe false
            estado.preguntas.size shouldBe 10
            estado.puntaje shouldBe 0
            estado.errorServidor shouldBe null
        }
    }

    "Se evitan consultas duplicadas mientras carga la partida" {

        runTest {

            val repository =
                mockk<PalabraRepository>()

            coEvery {
                repository.obtenerPalabras()
            } returns vocabulario

            val viewModel =
                JuegoViewModel(repository)

            viewModel.cargarPartidaServidor()
            viewModel.cargarPartidaServidor()

            viewModel.iniciarPartidaLocal()
            viewModel.reiniciarJuego()

            viewModel.estado.value.cargando shouldBe true

            advanceUntilIdle()

            coVerify(exactly = 1) {
                repository.obtenerPalabras()
            }

            viewModel.estado.value.origenServidor shouldBe true
            viewModel.estado.value.cargando shouldBe false
        }
    }
})
