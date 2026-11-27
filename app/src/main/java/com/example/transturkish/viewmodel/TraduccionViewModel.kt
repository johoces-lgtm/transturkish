package com.example.transturkish.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transturkish.model.TraduccionUiState
import com.example.transturkish.repository.TraduccionRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TraduccionViewModel(
    private val repository: TraduccionRepository =
        TraduccionRepository()
) : ViewModel() {

    private val _estado =
        MutableStateFlow(TraduccionUiState())

    val estado = _estado.asStateFlow()

    fun onTextoChange(valor: String) {

        if (_estado.value.cargando) return

        _estado.update {
            it.copy(
                texto = valor,
                traduccion = "",
                error = null
            )
        }
    }

    fun invertirIdiomas() {

        if (_estado.value.cargando) return

        _estado.update {
            it.copy(
                turcoAEspanol = !it.turcoAEspanol,
                texto = it.traduccion.ifBlank {
                    it.texto
                },
                traduccion = "",
                error = null
            )
        }
    }

    fun limpiar() {

        if (_estado.value.cargando) return

        _estado.update {
            it.copy(
                texto = "",
                traduccion = "",
                error = null
            )
        }
    }

    fun traducir() {

        val actual = _estado.value

        if (actual.cargando) return

        val texto = actual.texto.trim()

        if (texto.isBlank()) {

            _estado.update {
                it.copy(
                    error = "Ingresa un texto para traducir"
                )
            }

            return
        }

        if (texto.toByteArray(Charsets.UTF_8).size > 500) {

            _estado.update {
                it.copy(
                    error = "El texto supera el límite de 500 bytes"
                )
            }

            return
        }

        val idiomas = if (actual.turcoAEspanol) {
            "tr|es"
        } else {
            "es|tr"
        }

        _estado.update {
            it.copy(
                cargando = true,
                traduccion = "",
                error = null
            )
        }

        viewModelScope.launch {

            try {

                val resultado =
                    repository.traducir(
                        texto,
                        idiomas
                    )

                _estado.update {
                    it.copy(
                        traduccion = resultado,
                        cargando = false
                    )
                }

            } catch (e: CancellationException) {

                throw e

            } catch (e: Exception) {

                _estado.update {
                    it.copy(
                        cargando = false,
                        error = "No se pudo traducir. Comprueba tu conexión o intenta más tarde."
                    )
                }
            }
        }
    }
}
