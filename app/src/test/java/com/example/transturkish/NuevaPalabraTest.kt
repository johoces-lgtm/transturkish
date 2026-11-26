package com.example.transturkish

import com.example.transturkish.data.model.NuevaPalabra
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class NuevaPalabraTest : StringSpec({

    "Una palabra completa es valida" {

        val palabra = NuevaPalabra(
            "Kedi",
            "Gato"
        )

        palabra.esValida() shouldBe true
    }

    "Una palabra sin texto en turco no es valida" {

        val palabra = NuevaPalabra(
            "",
            "Gato"
        )

        palabra.esValida() shouldBe false
    }

    "Una palabra sin traduccion no es valida" {

        val palabra = NuevaPalabra(
            "Kedi",
            ""
        )

        palabra.esValida() shouldBe false
    }

    "Los campos con espacios no son validos" {

        val palabra = NuevaPalabra(
            "   ",
            "   "
        )

        palabra.esValida() shouldBe false
    }
})
