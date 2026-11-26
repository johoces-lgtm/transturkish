package com.example.transturkish

import com.example.transturkish.data.model.Palabra
import com.example.transturkish.model.generarPreguntas
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class GeneradorPreguntasTest : StringSpec({

    val vocabulario = listOf(
        Palabra(1L, "Merhaba", "Hola"),
        Palabra(2L, "Kedi", "Gato"),
        Palabra(3L, "Su", "Agua"),
        Palabra(4L, "Kitap", "Libro"),
        Palabra(5L, "Ev", "Casa")
    )

    "Cinco palabras generan cinco preguntas" {

        val preguntas = generarPreguntas(vocabulario)

        preguntas.size shouldBe 5
    }

    "Cada pregunta contiene tres alternativas diferentes" {

        val preguntas = generarPreguntas(vocabulario)

        preguntas.forEach { pregunta ->

            pregunta.alternativas.size shouldBe 3

            pregunta.alternativas
                .map { it.lowercase() }
                .distinct()
                .size shouldBe 3
        }
    }

    "Cada pregunta contiene su respuesta correcta" {

        val preguntas = generarPreguntas(vocabulario)

        preguntas.forEach { pregunta ->

            pregunta.alternativas.contains(
                pregunta.respuesta
            ) shouldBe true

            val original = vocabulario.first {
                it.turco == pregunta.palabra
            }

            pregunta.respuesta shouldBe original.espanol
        }
    }

    "Una lista vacia no genera preguntas" {

        generarPreguntas(
            emptyList()
        ).size shouldBe 0
    }

    "Dos palabras no son suficientes para jugar" {

        generarPreguntas(
            vocabulario.take(2)
        ).size shouldBe 0
    }

    "Las palabras repetidas o vacias se descartan" {

        val palabras = vocabulario + listOf(
            Palabra(6L, "Kedi", "Gato"),
            Palabra(7L, "", "Ejemplo"),
            Palabra(8L, "Ejemplo", "")
        )

        generarPreguntas(palabras).size shouldBe 5
    }

    "Las traducciones repetidas no permiten crear alternativas suficientes" {

        val palabras = listOf(
            Palabra(1L, "Palabra1", "Hola"),
            Palabra(2L, "Palabra2", "Hola"),
            Palabra(3L, "Palabra3", "Agua")
        )

        generarPreguntas(palabras).size shouldBe 0
    }

    "Una partida contiene como maximo diez preguntas" {

        val palabras = (1..15).map {

            Palabra(
                it.toLong(),
                "Palabra $it",
                "Traduccion $it"
            )
        }

        generarPreguntas(palabras).size shouldBe 10
    }
})
