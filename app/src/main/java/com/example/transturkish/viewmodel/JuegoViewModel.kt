package com.example.transturkish.viewmodel

import androidx.lifecycle.ViewModel
import com.example.transturkish.model.JuegoUiState
import com.example.transturkish.model.PreguntaJuego
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import com.example.transturkish.model.generarPreguntas
import com.example.transturkish.repository.PalabraRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class JuegoViewModel : ViewModel() {

    // Vocabulario local mientras integramos la API
    private val preguntas = listOf(

        PreguntaJuego(
            "Merhaba",
            "Hola",
            listOf("Hola", "Gracias", "Agua")
        ),

        PreguntaJuego(
            "Teşekkürler",
            "Gracias",
            listOf("Casa", "Gracias", "Gato")
        ),

        PreguntaJuego(
            "Su",
            "Agua",
            listOf("Libro", "Hola", "Agua")
        ),

        PreguntaJuego(
            "Kedi",
            "Gato",
            listOf("Gato", "Perro", "Casa")
        ),

        PreguntaJuego(
            "Köpek",
            "Perro",
            listOf("Perro", "Libro", "Agua")
        ),

        PreguntaJuego(
            "Günaydın",
            "Buenos días",
            listOf(
                "Buenas noches",
                "Buenos días",
                "Muchas gracias"
            )
        ),

        PreguntaJuego(
            "Kitap",
            "Libro",
            listOf("Libro", "Escuela", "Casa")
        ),

        PreguntaJuego(
            "Ev",
            "Casa",
            listOf("Agua", "Casa", "Amigo")
        ),

        PreguntaJuego(
            "Arkadaş",
            "Amigo",
            listOf("Profesor", "Amigo", "Perro")
        ),

        PreguntaJuego(
            "Okul",
            "Escuela",
            listOf("Escuela", "Libro", "Casa")
        )
    )

    // Estado interno del juego
    private val _estado = MutableStateFlow(
        crearPartida()
    )

    // Estado observable desde la interfaz
    val estado = _estado.asStateFlow()

    // Crear una partida con preguntas aleatorias
    private fun crearPartida(
        listaPreguntas: List<PreguntaJuego> = preguntas,
        origenServidor: Boolean = false
    ): JuegoUiState {

        val preguntasAleatorias = listaPreguntas
            .shuffled()
            .map { pregunta ->
                pregunta.copy(
                    alternativas =
                        pregunta.alternativas.shuffled()
                )
            }

        return JuegoUiState(
            preguntas = preguntasAleatorias,
            origenServidor = origenServidor
        )
    }

    // Iniciar una partida utilizando nuestra API REST
    fun cargarPartidaServidor() {

        if (_estado.value.cargando) return

        _estado.update {
            it.copy(
                cargando = true,
                errorServidor = null
            )
        }

        viewModelScope.launch {

            try {

                val palabras =
                    PalabraRepository().obtenerPalabras()

                val nuevasPreguntas =
                    generarPreguntas(palabras)

                if (nuevasPreguntas.isEmpty()) {

                    _estado.update {
                        it.copy(
                            cargando = false,
                            errorServidor =
                                "Se necesitan al menos tres traducciones diferentes"
                        )
                    }

                } else {

                    _estado.value = crearPartida(
                        listaPreguntas = nuevasPreguntas,
                        origenServidor = true
                    )
                }

            } catch (e: CancellationException) {

                throw e

            } catch (e: Exception) {

                _estado.update {
                    it.copy(
                        cargando = false,
                        errorServidor =
                            "No se pudo conectar con el servidor. Puedes practicar sin conexión."
                    )
                }
            }
        }
    }

    // Iniciar una partida con las preguntas originales
    fun iniciarPartidaLocal() {

        if (_estado.value.cargando) return

        _estado.value = crearPartida()
    }

    // Comprobar la respuesta seleccionada
    fun responder(opcion: String) {

        val actual = _estado.value

        // Evitar responder más de una vez
        if (actual.seleccionada != null) {
            return
        }

        // Evitar acceder a una pregunta inexistente
        if (actual.preguntaActual >= actual.preguntas.size) {
            return
        }

        val pregunta = actual.preguntas[
            actual.preguntaActual
        ]

        if (opcion !in pregunta.alternativas) {
            return
        }

        val esCorrecta = opcion == pregunta.respuesta

        _estado.update {
            it.copy(
                seleccionada = opcion,
                puntaje = if (esCorrecta) {
                    it.puntaje + 1
                } else {
                    it.puntaje
                }
            )
        }
    }

    // Avanzar a la siguiente pregunta
    fun siguientePregunta() {

        val actual = _estado.value

        if (
            actual.seleccionada == null ||
            actual.preguntaActual >= actual.preguntas.size
        ) {
            return
        }

        _estado.update {
            it.copy(
                preguntaActual = it.preguntaActual + 1,
                seleccionada = null
            )
        }
    }

    // Reiniciar completamente la partida
    fun reiniciarJuego() {

        val actual = _estado.value

        if (actual.cargando) return

        if (actual.origenServidor) {

            _estado.value = crearPartida(
                listaPreguntas = actual.preguntas,
                origenServidor = true
            )

        } else {

            _estado.value = crearPartida()
        }
    }
}
