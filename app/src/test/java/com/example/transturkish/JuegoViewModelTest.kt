package com.example.transturkish

import com.example.transturkish.viewmodel.JuegoViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class JuegoViewModelTest : StringSpec({

    "La partida comienza con diez preguntas" {

        val viewModel = JuegoViewModel()

        viewModel.estado.value.preguntas.size shouldBe 10
    }

    "El puntaje inicial debe ser cero" {

        val viewModel = JuegoViewModel()

        viewModel.estado.value.puntaje shouldBe 0
    }

    "La partida comienza en la primera pregunta" {

        val viewModel = JuegoViewModel()

        viewModel.estado.value.preguntaActual shouldBe 0
    }

    "Las preguntas contienen tres alternativas y una respuesta correcta" {

        val viewModel = JuegoViewModel()

        viewModel.estado.value.preguntas.forEach { pregunta ->

            pregunta.alternativas.size shouldBe 3

            pregunta.alternativas.contains(
                pregunta.respuesta
            ) shouldBe true
        }
    }

    "Una respuesta correcta aumenta el puntaje" {

        val viewModel = JuegoViewModel()

        val pregunta = viewModel.estado.value.preguntas[0]

        viewModel.responder(pregunta.respuesta)

        viewModel.estado.value.puntaje shouldBe 1
    }

    "Una respuesta incorrecta no aumenta el puntaje" {

        val viewModel = JuegoViewModel()

        val pregunta = viewModel.estado.value.preguntas[0]

        val incorrecta = pregunta.alternativas.first {
            it != pregunta.respuesta
        }

        viewModel.responder(incorrecta)

        viewModel.estado.value.puntaje shouldBe 0
    }

    "No se puede responder dos veces la misma pregunta" {

        val viewModel = JuegoViewModel()

        val pregunta = viewModel.estado.value.preguntas[0]

        viewModel.responder(pregunta.respuesta)
        viewModel.responder(pregunta.respuesta)

        viewModel.estado.value.puntaje shouldBe 1
    }

    "La alternativa seleccionada queda registrada" {

        val viewModel = JuegoViewModel()

        val pregunta = viewModel.estado.value.preguntas[0]

        viewModel.responder(pregunta.respuesta)

        viewModel.estado.value.seleccionada shouldBe
                pregunta.respuesta
    }

    "Una alternativa inexistente no se acepta" {

        val viewModel = JuegoViewModel()

        viewModel.responder("Alternativa inexistente")

        viewModel.estado.value.puntaje shouldBe 0

        viewModel.estado.value.seleccionada shouldBe null
    }

    "No se puede avanzar sin responder" {

        val viewModel = JuegoViewModel()

        viewModel.siguientePregunta()

        viewModel.estado.value.preguntaActual shouldBe 0
    }

    "Se puede avanzar despues de responder" {

        val viewModel = JuegoViewModel()

        val pregunta = viewModel.estado.value.preguntas[0]

        viewModel.responder(pregunta.respuesta)

        viewModel.siguientePregunta()

        viewModel.estado.value.preguntaActual shouldBe 1

        viewModel.estado.value.seleccionada shouldBe null
    }

    "Una partida perfecta obtiene diez puntos" {

        val viewModel = JuegoViewModel()

        repeat(10) {

            val estado = viewModel.estado.value

            val pregunta = estado.preguntas[
                estado.preguntaActual
            ]

            viewModel.responder(pregunta.respuesta)

            viewModel.siguientePregunta()
        }

        viewModel.estado.value.puntaje shouldBe 10

        viewModel.estado.value.preguntaActual shouldBe 10
    }

    "No se puede responder despues de terminar la partida" {

        val viewModel = JuegoViewModel()

        repeat(10) {

            val estado = viewModel.estado.value

            val pregunta = estado.preguntas[
                estado.preguntaActual
            ]

            viewModel.responder(pregunta.respuesta)

            viewModel.siguientePregunta()
        }

        viewModel.responder("Hola")

        viewModel.estado.value.puntaje shouldBe 10
    }

    "Reiniciar el juego restablece el estado inicial" {

        val viewModel = JuegoViewModel()

        val pregunta = viewModel.estado.value.preguntas[0]

        viewModel.responder(pregunta.respuesta)

        viewModel.siguientePregunta()

        viewModel.reiniciarJuego()

        viewModel.estado.value.puntaje shouldBe 0

        viewModel.estado.value.preguntaActual shouldBe 0

        viewModel.estado.value.seleccionada shouldBe null

        viewModel.estado.value.preguntas.size shouldBe 10
    }
})
