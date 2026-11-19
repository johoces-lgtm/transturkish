package com.example.transturkish.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.transturkish.data.ImagenDataStore
import com.example.transturkish.data.ImagenStorage
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImagenViewModel(application: Application) : AndroidViewModel(application) {

    // Permite guardar y recuperar rutas mediante DataStore
    private val imagenDataStore = ImagenDataStore(application)

    // Permite guardar y recuperar archivos físicos
    private val imagenStorage = ImagenStorage(application)

    // Identificador temporal mientras implementamos el inicio de sesión
    private val usuarioIdTemporal = "invitado"


    // Estado privado de la imagen seleccionada
    private val _imagenUri = MutableStateFlow<Uri?>(null)

    // Estado público que podrá observar la pantalla
    val imagenUri: StateFlow<Uri?> = _imagenUri.asStateFlow()

    // Al iniciar el ViewModel, recuperamos la fotografía guardada
    init {
        cargarImagenGuardada()
    }

    // Recupera desde DataStore la ruta de la fotografía
    private fun cargarImagenGuardada() {
        viewModelScope.launch {

            val ruta = imagenDataStore
                .obtenerRutaFoto(usuarioIdTemporal)
                .first()

            _imagenUri.value = ruta?.let {
                imagenStorage.obtenerUriImagen(it)
            }
        }
    }



    fun actualizarImagen(uri: Uri) {

        viewModelScope.launch {
            try {
                // Guardamos la fotografía en el almacenamiento interno
                val archivo = withContext(Dispatchers.IO) {
                    imagenStorage.guardarImagen(uri, usuarioIdTemporal)
                }

                // Guardamos su ubicación en DataStore
                imagenDataStore.guardarRutaFoto(
                    usuarioIdTemporal,
                    archivo.absolutePath
                )

                // Actualizamos el estado de la fotografía
                _imagenUri.value = imagenStorage.obtenerUriImagen(
                    archivo.absolutePath
                )

            } catch (e: Exception) {
                // Conservamos el estado anterior si ocurre un error
                // Más adelante mostraremos un mensaje al usuario
            }
        }
    }



    fun eliminarImagen() {
        viewModelScope.launch {
            try {
                // Eliminamos la fotografía del almacenamiento interno
                val eliminada = withContext(Dispatchers.IO) {
                    imagenStorage.eliminarImagen(usuarioIdTemporal)
                }

                if (eliminada) {
                    // Eliminamos la ruta guardada en DataStore
                    imagenDataStore.eliminarRutaFoto(usuarioIdTemporal)

                    // Quitamos la fotografía de la pantalla
                    _imagenUri.value = null
                }

            } catch (e: Exception) {
                // Registramos el error para poder investigarlo
                android.util.Log.e(
                    "ImagenViewModel",
                    "Error al eliminar fotografía",
                    e
                )
            }
        }
    }

}
