package com.example.transturkish.data

import android.content.Context
import java.io.File
import android.net.Uri
import android.util.AtomicFile

class ImagenStorage(private val context: Context) {

    // Carpeta privada para guardar las fotografías
    private val carpetaImagenes = File(
        context.filesDir,
        "imagenes_perfil"
    )

    // Valida el identificador y genera el nombre del archivo
    private fun nombreArchivo(usuarioId: String): String {
        require(usuarioId.matches(Regex("[A-Za-z0-9_-]+"))) {
            "Identificador de usuario no válido"
        }

        return "perfil_$usuarioId.jpg"
    }



    fun guardarImagen(uriOrigen: Uri, usuarioId: String): File {

        // Crear carpeta si no existe
        carpetaImagenes.mkdirs()

        // Archivo correspondiente al usuario
        val archivoDestino = File(
            carpetaImagenes,
            nombreArchivo(usuarioId)
        )

        // Protege la fotografía anterior durante la escritura
        val archivoAtomico = AtomicFile(archivoDestino)

        // Abrir fotografía original
        val entrada = context.contentResolver.openInputStream(uriOrigen)
            ?: throw IllegalArgumentException(
                "No se pudo abrir la imagen seleccionada"
            )

        entrada.use { origen ->

            val salida = archivoAtomico.startWrite()

            try {
                // Copiar los datos al archivo protegido
                origen.copyTo(salida)

                // Confirmar que la escritura terminó
                archivoAtomico.finishWrite(salida)

            } catch (e: Exception) {

                // Recuperar el archivo anterior si falla
                archivoAtomico.failWrite(salida)
                throw e
            }
        }

        return archivoDestino
    }

    // Recupera la URI de una fotografía guardada
    fun obtenerUriImagen(ruta: String): Uri? {

        val archivo = File(ruta)

        return if (archivo.exists() && archivo.isFile) {
            Uri.fromFile(archivo)
        } else {
            null
        }
    }

    // Elimina el archivo físico de la fotografía de un usuario
    fun eliminarImagen(usuarioId: String): Boolean {

        val archivo = File(
            carpetaImagenes,
            nombreArchivo(usuarioId)
        )

        return if (archivo.exists()) {
            archivo.delete()
        } else {
            true
        }
    }
}
