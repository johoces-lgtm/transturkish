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
    val mensaje: String? = null
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
                    errorFormulario =
                        "Completa ambos campos",
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

                repository.crearPalabra(nuevaPalabra)

                _estado.update {
                    it.copy(
                        turco = "",
                        espanol = "",
                        cargando = false,
                        mensaje = "Palabra guardada correctamente"
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
}
