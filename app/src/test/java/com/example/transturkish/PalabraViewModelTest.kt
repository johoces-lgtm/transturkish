package com.example.transturkish

import com.example.transturkish.data.model.Palabra
import com.example.transturkish.viewmodel.PalabraViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PalabraViewModelTest : StringSpec({

    "El formulario comienza en modo creacion" {

        val viewModel = PalabraViewModel()

        viewModel.estado.value.editandoId shouldBe null
        viewModel.estado.value.turco shouldBe ""
        viewModel.estado.value.espanol shouldBe ""
    }

    "Editar una palabra carga sus datos en el formulario" {

        val viewModel = PalabraViewModel()

        val palabra = Palabra(
            1L,
            "Kedi",
            "Gato"
        )

        viewModel.iniciarEdicion(palabra)

        val estado = viewModel.estado.value

        estado.editandoId shouldBe 1L
        estado.turco shouldBe "Kedi"
        estado.espanol shouldBe "Gato"
    }

    "Cancelar la edicion limpia el formulario" {

        val viewModel = PalabraViewModel()

        viewModel.iniciarEdicion(
            Palabra(1L, "Kedi", "Gato")
        )

        viewModel.cancelarEdicion()

        val estado = viewModel.estado.value

        estado.editandoId shouldBe null
        estado.turco shouldBe ""
        estado.espanol shouldBe ""
    }

    "Editar una palabra con campos vacios genera un error" {

        val viewModel = PalabraViewModel()

        viewModel.iniciarEdicion(
            Palabra(1L, "Kedi", "Gato")
        )

        viewModel.onEspanolChange("")

        viewModel.guardarPalabra()

        viewModel.estado.value.errorFormulario shouldBe
                "Completa ambos campos"

        viewModel.estado.value.editandoId shouldBe 1L

        viewModel.estado.value.cargando shouldBe false
    }

    "Corregir un campo elimina el error de validacion" {

        val viewModel = PalabraViewModel()

        viewModel.guardarPalabra()

        viewModel.onTurcoChange("Kedi")
        viewModel.onEspanolChange("Gato")

        viewModel.estado.value.errorFormulario shouldBe null
    }
})
