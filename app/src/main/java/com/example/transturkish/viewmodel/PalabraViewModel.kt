package com.example.transturkish.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transturkish.data.model.NuevaPalabra
import com.example.transturkish.data.model.Palabra
import com.example.transturkish.repository.PalabraRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PalabraUiState(
    val palabras: List<Palabra> = emptyList(),
    val cargando: Boolean = false,
    val consultaRealizada: Boolean = false,
    val error: String? = null,

    val turco: String = "",
    val espanol: String = "",
    val errorFormulario: String? = null,
    val mensaje: String? = null,

    // Identificador de la palabra que estamos editando
    val editandoId: Long? = null
)

class PalabraViewModel : ViewModel() {

    private val repository = PalabraRepository()

    private val _estado =
        MutableStateFlow(PalabraUiState())

    val estado = _estado.asStateFlow()

    fun onTurcoChange(valor: String) {

        _estado.update {
            it.copy(
                turco = valor,
                errorFormulario = null,
                mensaje = null
            )
        }
    }

    fun onEspanolChange(valor: String) {

        _estado.update {
            it.copy(
                espanol = valor,
                errorFormulario = null,
                mensaje = null
            )
        }
    }

    // Cargar el vocabulario desde Spring Boot
    fun cargarPalabras() {

        if (_estado.value.cargando) return

        _estado.update {
            it.copy(
                cargando = true,
                error = null
            )
        }

        viewModelScope.launch {

            try {

                val palabras =
                    repository.obtenerPalabras()

                _estado.update {
                    it.copy(
                        palabras = palabras,
                        cargando = false,
                        consultaRealizada = true
                    )
                }

            } catch (e: CancellationException) {

                throw e

            } catch (e: Exception) {

                _estado.update {
                    it.copy(
                        cargando = false,
                        error = "No se pudo consultar el vocabulario"
                    )
                }
            }
        }
    }

    // Cargar una palabra en el formulario de edición
    fun iniciarEdicion(palabra: Palabra) {

        if (_estado.value.cargando) return

        _estado.update {
            it.copy(
                editandoId = palabra.id,
                turco = palabra.turco,
                espanol = palabra.espanol,
                errorFormulario = null,
                mensaje = null,
                error = null
            )
        }
    }

    // Cancelar la edición y limpiar el formulario
    fun cancelarEdicion() {

        if (_estado.value.cargando) return

        _estado.update {
            it.copy(
                editandoId = null,
                turco = "",
                espanol = "",
                errorFormulario = null,
                mensaje = null,
                error = null
            )
        }
    }

    // Crear o actualizar una palabra
    fun guardarPalabra() {

        val actual = _estado.value

        if (actual.cargando) return

        val nuevaPalabra = NuevaPalabra(
            turco = actual.turco.trim(),
            espanol = actual.espanol.trim()
        )

        if (!nuevaPalabra.esValida()) {

            _estado.update {
                it.copy(
                    errorFormulario = "Completa ambos campos",
                    mensaje = null
                )
            }

            return
        }

        _estado.update {
            it.copy(
                cargando = true,
                error = null,
                errorFormulario = null,
                mensaje = null
            )
        }

        viewModelScope.launch {

            try {

                val id = actual.editandoId

                if (id == null) {

                    repository.crearPalabra(nuevaPalabra)

                } else {

                    repository.actualizarPalabra(
                        id,
                        nuevaPalabra
                    )
                }

                _estado.update {
                    it.copy(
                        turco = "",
                        espanol = "",
                        editandoId = null,
                        cargando = false,
                        mensaje = if (id == null) {
                            "Palabra guardada correctamente"
                        } else {
                            "Palabra actualizada correctamente"
                        }
                    )
                }

                cargarPalabras()

            } catch (e: CancellationException) {

                throw e

            } catch (e: Exception) {

                _estado.update {
                    it.copy(
                        cargando = false,
                        error = "No se pudo guardar la palabra. Actualiza el diccionario antes de reintentar."
                    )
                }
            }
        }
    }

    // Eliminar una palabra del servidor
    fun eliminarPalabra(id: Long) {

        if (_estado.value.cargando) return

        _estado.update {
            it.copy(
                cargando = true,
                error = null,
                mensaje = null
            )
        }

        viewModelScope.launch {

            try {

                repository.eliminarPalabra(id)

                _estado.update {

                    val eliminandoEditada =
                        it.editandoId == id

                    it.copy(
                        cargando = false,
                        editandoId = if (eliminandoEditada) {
                            null
                        } else {
                            it.editandoId
                        },
                        turco = if (eliminandoEditada) {
                            ""
                        } else {
                            it.turco
                        },
                        espanol = if (eliminandoEditada) {
                            ""
                        } else {
                            it.espanol
                        },
                        errorFormulario = if (eliminandoEditada) {
                            null
                        } else {
                            it.errorFormulario
                        },
                        mensaje = "Palabra eliminada correctamente"
                    )
                }

                cargarPalabras()

            } catch (e: CancellationException) {

                throw e

            } catch (e: Exception) {

                _estado.update {
                    it.copy(
                        cargando = false,
                        error = "No se pudo eliminar la palabra. Actualiza el diccionario antes de reintentar."
                    )
                }
            }
        }
    }
}
