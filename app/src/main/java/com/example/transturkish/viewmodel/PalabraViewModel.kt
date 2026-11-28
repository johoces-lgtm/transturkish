package com.example.transturkish.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transturkish.data.model.Palabra
import com.example.transturkish.data.repository.PalabraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PalabraViewModel(
    private val repository: PalabraRepository
) : ViewModel() {

    private val _palabras = MutableStateFlow<List<Palabra>>(emptyList())
    val palabras: StateFlow<List<Palabra>> = _palabras

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun cargarPalabras() {
        viewModelScope.launch {
            _cargando.value = true
            _error.value = null

            try {
                _palabras.value = repository.obtenerPalabras()
            } catch (e: Exception) {
                _error.value = e.message ?: "No se pudieron cargar las palabras"
            } finally {
                _cargando.value = false
            }
        }
    }

    fun agregarPalabra(turco: String, espanol: String) {
        if (turco.isBlank() || espanol.isBlank()) {
            _error.value = "Debes completar ambos campos"
            return
        }

        viewModelScope.launch {
            _error.value = null

            try {
                repository.crearPalabra(
                    Palabra(
                        turco = turco.trim(),
                        espanol = espanol.trim()
                    )
                )
                cargarPalabras()
            } catch (e: Exception) {
                _error.value = e.message ?: "No se pudo agregar la palabra"
            }
        }
    }

    fun actualizarPalabra(
        id: Long,
        turco: String,
        espanol: String
    ) {
        if (turco.isBlank() || espanol.isBlank()) {
            _error.value = "Debes completar ambos campos"
            return
        }

        viewModelScope.launch {
            _error.value = null

            try {
                repository.actualizarPalabra(
                    id,
                    Palabra(
                        id = id,
                        turco = turco.trim(),
                        espanol = espanol.trim()
                    )
                )
                cargarPalabras()
            } catch (e: Exception) {
                _error.value = e.message ?: "No se pudo actualizar la palabra"
            }
        }
    }

    fun eliminarPalabra(id: Long) {
        viewModelScope.launch {
            _error.value = null

            try {
                repository.eliminarPalabra(id)
                cargarPalabras()
            } catch (e: Exception) {
                _error.value = e.message ?: "No se pudo eliminar la palabra"
            }
        }
    }
}
