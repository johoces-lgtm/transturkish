package com.example.transturkish

import com.example.transturkish.viewmodel.LoginViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class LoginViewModelTest : StringSpec({

    "El formulario comienza con los campos vacios" {
        val viewModel = LoginViewModel()

        viewModel.estado.value.correo shouldBe ""
        viewModel.estado.value.clave shouldBe ""
        viewModel.estado.value.errorCorreo shouldBe null
        viewModel.estado.value.errorClave shouldBe null
    }

    "El correo ingresado se guarda en el estado" {
        val viewModel = LoginViewModel()

        viewModel.onCorreoChange("prueba@ejemplo.cl")

        viewModel.estado.value.correo shouldBe
                "prueba@ejemplo.cl"
    }

    "La clave ingresada se guarda temporalmente" {
        val viewModel = LoginViewModel()

        viewModel.onClaveChange("Prueba123")

        viewModel.estado.value.clave shouldBe "Prueba123"
    }

    "Los campos vacios generan errores" {
        val viewModel = LoginViewModel()

        viewModel.intentarInicioSesion()

        viewModel.estado.value.errorCorreo shouldBe
                "Ingresa un correo válido"

        viewModel.estado.value.errorClave shouldBe
                "La contraseña debe tener al menos 6 caracteres"
    }

    "Un correo invalido genera un error" {
        val viewModel = LoginViewModel()

        viewModel.onCorreoChange("correoIncorrecto")
        viewModel.onClaveChange("Prueba123")

        viewModel.intentarInicioSesion()

        viewModel.estado.value.errorCorreo shouldBe
                "Ingresa un correo válido"
    }

    "Una clave demasiado corta genera un error" {
        val viewModel = LoginViewModel()

        viewModel.onCorreoChange("prueba@ejemplo.cl")
        viewModel.onClaveChange("123")

        viewModel.intentarInicioSesion()

        viewModel.estado.value.errorClave shouldBe
                "La contraseña debe tener al menos 6 caracteres"
    }

    "Los datos validos superan la validacion local" {
        val viewModel = LoginViewModel()

        viewModel.onCorreoChange("prueba@ejemplo.cl")
        viewModel.onClaveChange("Prueba123")

        viewModel.intentarInicioSesion()

        viewModel.estado.value.errorCorreo shouldBe null
        viewModel.estado.value.errorClave shouldBe null

        viewModel.estado.value.mensaje shouldBe
                "Autenticación con el servidor pendiente"
    }

    "Corregir los campos elimina sus errores" {
        val viewModel = LoginViewModel()

        viewModel.intentarInicioSesion()

        viewModel.onCorreoChange("prueba@ejemplo.cl")
        viewModel.onClaveChange("Prueba123")

        viewModel.estado.value.errorCorreo shouldBe null
        viewModel.estado.value.errorClave shouldBe null
    }
})