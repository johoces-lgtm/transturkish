package com.example.transturkish.viewmodel

import androidx.lifecycle.ViewModel
import com.example.transturkish.model.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    // Estado interno modificable
    private val _estado = MutableStateFlow(LoginUiState())

    // Estado público de solo lectura
    val estado = _estado.asStateFlow()

    fun onCorreoChange(valor: String) {
        _estado.update {
            it.copy(
                correo = valor,
                errorCorreo = null,
                mensaje = null
            )
        }
    }

    fun onClaveChange(valor: String) {
        _estado.update {
            it.copy(
                clave = valor,
                errorClave = null,
                mensaje = null
            )
        }
    }

    // Validaciones del formulario
    fun intentarInicioSesion() {

        val estadoActual = _estado.value

        val correo = estadoActual.correo.trim()

        val errorCorreo = if (
            correo.isBlank() ||
            !correo.contains("@") ||
            correo.substringBefore("@").isBlank() ||
            !correo.substringAfter("@").contains(".")
        ) {
            "Ingresa un correo válido"
        } else {
            null
        }

        val errorClave = if (estadoActual.clave.length < 6) {
            "La contraseña debe tener al menos 6 caracteres"
        } else {
            null
        }

        val formularioValido =
            errorCorreo == null && errorClave == null

        _estado.update {
            it.copy(
                errorCorreo = errorCorreo,
                errorClave = errorClave,
                mensaje = if (formularioValido) {
                    "Autenticación con el servidor pendiente"
                } else {
                    null
                }
            )
        }
    }
}
