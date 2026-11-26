package com.example.transturkish.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transturkish.data.model.Palabra
import com.example.transturkish.repository.PalabraRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PalabraUiState(
    val palabras: List<Palabra> = emptyList(),
    val cargando: Boolean = false,
    val consultaRealizada: Boolean = false,
    val error: String? = null
)

class PalabraViewModel : ViewModel() {

    private val repository = PalabraRepository()

    private val _estado =
        MutableStateFlow(PalabraUiState())

    val estado = _estado.asStateFlow()

    fun cargarPalabras() {

        if (_estado.value.cargando) return

        _estado.value = PalabraUiState(
            cargando = true
        )

        viewModelScope.launch {

            try {

                val palabras =
                    repository.obtenerPalabras()

                _estado.value = PalabraUiState(
                    palabras = palabras,
                    consultaRealizada = true
                )

            } catch (e: CancellationException) {

                throw e

            } catch (e: Exception) {

                _estado.value = PalabraUiState(
                    consultaRealizada = true,
                    error = "No se pudo conectar con el servidor"
                )
            }
        }
    }
}
