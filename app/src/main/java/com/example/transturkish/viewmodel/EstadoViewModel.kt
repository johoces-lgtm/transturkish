package com.example.transturkish.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.transturkish.data.EstadoDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// ViewModel extendido desde AndroidViewModel porque usaremos contexto
class EstadoViewModel(application: Application): AndroidViewModel(application) {

    // DataStore creado con contexto de aplicación
    private val estadoDataStore = EstadoDataStore(context = application)

    // Estado que representa si está activado o no (observable)
    private val _activo = MutableStateFlow<Boolean?>(value = null)
    val activo: StateFlow<Boolean?> = _activo

    // Estado para mostrar u ocultar el mensaje animado
    private val _mostrarMensaje = MutableStateFlow(value = false)
    val mostrarMensaje: StateFlow<Boolean> = _mostrarMensaje

    init {
        // Al iniciar el ViewModel, cargamos el estado desde DataStore
        cargarEstado()
    }

    private fun cargarEstado() {
        viewModelScope.launch {
            // Simula demora para mostrar Loader
            delay(timeMillis = 1500)
            _activo.value = estadoDataStore.obtenerEstado().first() ?: false
        }
    }

    fun alternarEstado() {
        viewModelScope.launch {
            // Alternamos el valor actual (negamos el valor actual con '!')
            val nuevoValor = !(_activo.value ?: false)

            // Guardamos en DataStore
            estadoDataStore.guardarEstado(nuevoValor)

            // Actualizamos el flujo
            _activo.value = nuevoValor

            // Mostramos el mensaje visual animado
            _mostrarMensaje.value = true

            // Ocultamos después de 2 segundos
            delay(timeMillis = 2000)
            _mostrarMensaje.value = false
        }
    }
}