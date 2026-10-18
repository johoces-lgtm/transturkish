package com.example.transturkish.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ImagenViewModel : ViewModel() {

    // Estado privado de la imagen seleccionada
    private val _imagenUri = MutableStateFlow<Uri?>(null)

    // Estado público que podrá observar la pantalla
    val imagenUri: StateFlow<Uri?> = _imagenUri.asStateFlow()

    // Actualiza la imagen seleccionada o capturada
    fun actualizarImagen(uri: Uri) {
        _imagenUri.value = uri
    }

    // Elimina la imagen del estado
    fun eliminarImagen() {
        _imagenUri.value = null
    }
}
