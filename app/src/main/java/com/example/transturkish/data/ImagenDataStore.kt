package com.example.transturkish.data

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ImagenDataStore(private val context: Context) {

    // Clave para identificar la foto de cada usuario
    private fun claveFoto(usuarioId: String) =
        stringPreferencesKey("foto_perfil_$usuarioId")


    // Guarda la ruta de la foto correspondiente a un usuario
    suspend fun guardarRutaFoto(usuarioId: String, ruta: String) {
        context.dataStore.edit { preferencias ->
            preferencias[claveFoto(usuarioId)] = ruta
        }
    }

    // Recupera la ruta de la fotografía de un usuario
    fun obtenerRutaFoto(usuarioId: String): Flow<String?> {
        return context.dataStore.data.map { preferencias ->
            preferencias[claveFoto(usuarioId)]
        }
    }

    // Elimina la ruta guardada de la fotografía de un usuario
    suspend fun eliminarRutaFoto(usuarioId: String) {
        context.dataStore.edit { preferencias ->
            preferencias.remove(claveFoto(usuarioId))
        }
    }
}
