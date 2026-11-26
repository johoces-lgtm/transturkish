package com.example.transturkish

import com.example.transturkish.viewmodel.UsuarioViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class UsuarioViewModelTest : StringSpec({

    "El formulario comienza vacio" {
        val viewModel = UsuarioViewModel()

        viewModel.estado.value.nombre shouldBe ""
        viewModel.estado.value.correo shouldBe ""
        viewModel.estado.value.clave shouldBe ""
        viewModel.estado.value.direccion shouldBe ""
        viewModel.estado.value.aceptaTerminos shouldBe false
    }

    "Los datos ingresados se actualizan correctamente" {
        val viewModel = UsuarioViewModel()

        viewModel.onNombreChange("Usuario de prueba")
        viewModel.onCorreoChange("prueba@ejemplo.cl")
        viewModel.onClaveChange("Prueba123")
        viewModel.onDireccionChange("Santiago")
        viewModel.onAceptarTerminosChange(true)

        val estado = viewModel.estado.value

        estado.nombre shouldBe "Usuario de prueba"
        estado.correo shouldBe "prueba@ejemplo.cl"
        estado.clave shouldBe "Prueba123"
        estado.direccion shouldBe "Santiago"
        estado.aceptaTerminos shouldBe true
    }

    "Un formulario vacio no es valido" {
        val viewModel = UsuarioViewModel()

        viewModel.validarFormulario() shouldBe false

        val errores = viewModel.estado.value.errores

        errores.nombre shouldBe "Campo obligatorio"
        errores.correo shouldBe "Correo inválido"
        errores.clave shouldBe "Debe tener al menos 6 caracteres"
        errores.direccion shouldBe "Campo obligatorio"
    }

    "Un correo incorrecto no permite continuar" {
        val viewModel = UsuarioViewModel()

        viewModel.onCorreoChange("correoIncorrecto")

        viewModel.validarFormulario() shouldBe false

        viewModel.estado.value.errores.correo shouldBe
                "Correo inválido"
    }

    "Una clave corta genera un error" {
        val viewModel = UsuarioViewModel()

        viewModel.onClaveChange("123")

        viewModel.validarFormulario() shouldBe false

        viewModel.estado.value.errores.clave shouldBe
                "Debe tener al menos 6 caracteres"
    }

    "No se puede continuar sin aceptar los terminos" {
        val viewModel = UsuarioViewModel()

        viewModel.onNombreChange("Usuario de prueba")
        viewModel.onCorreoChange("prueba@ejemplo.cl")
        viewModel.onClaveChange("Prueba123")
        viewModel.onDireccionChange("Santiago")
        viewModel.onAceptarTerminosChange(false)

        viewModel.validarFormulario() shouldBe false
    }

    "Un formulario completo y valido permite continuar" {
        val viewModel = UsuarioViewModel()

        viewModel.onNombreChange("Usuario de prueba")
        viewModel.onCorreoChange("prueba@ejemplo.cl")
        viewModel.onClaveChange("Prueba123")
        viewModel.onDireccionChange("Santiago")
        viewModel.onAceptarTerminosChange(true)

        viewModel.validarFormulario() shouldBe true

        val errores = viewModel.estado.value.errores

        errores.nombre shouldBe null
        errores.correo shouldBe null
        errores.clave shouldBe null
        errores.direccion shouldBe null
    }

    "Corregir los datos elimina los errores del formulario" {
        val viewModel = UsuarioViewModel()

        viewModel.validarFormulario()

        viewModel.onNombreChange("Usuario de prueba")
        viewModel.onCorreoChange("prueba@ejemplo.cl")
        viewModel.onClaveChange("Prueba123")
        viewModel.onDireccionChange("Santiago")

        val errores = viewModel.estado.value.errores

        errores.nombre shouldBe null
        errores.correo shouldBe null
        errores.clave shouldBe null
        errores.direccion shouldBe null
    }
})