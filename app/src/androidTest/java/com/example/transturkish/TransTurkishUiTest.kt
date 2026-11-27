package com.example.transturkish

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TransTurkishUiTest {

    @get:Rule
    val composeTestRule =
        createAndroidComposeRule<MainActivity>()

    // Prueba 1: Navegación al selector
    @Test
    fun abrirSelectorDeModalidades() {

        composeTestRule
            .onNodeWithText("Comenzar partida")
            .performScrollTo()
            .performClick()

        composeTestRule
            .onNodeWithText("Elige cómo practicar")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Práctica conectada")
            .performScrollTo()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Práctica sin conexión")
            .performScrollTo()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Pregunta 1 de 10")
            .assertDoesNotExist()
    }

    // Prueba 2: Comenzar una partida local
    @Test
    fun comenzarPartidaSinConexion() {

        composeTestRule
            .onNodeWithText("Comenzar partida")
            .performScrollTo()
            .performClick()

        composeTestRule
            .onAllNodesWithText("Comenzar")[1]
            .performScrollTo()
            .performClick()

        composeTestRule
            .onNodeWithText("Pregunta 1 de 10")
            .performScrollTo()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Vocabulario local")
            .performScrollTo()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Volver al inicio")
            .performScrollTo()
            .performClick()

        composeTestRule
            .onNodeWithText("Comenzar partida")
            .performScrollTo()
            .assertIsDisplayed()
    }

    // Prueba 3: Validación visual del traductor
    @Test
    fun traductorValidaTextoVacio() {

        composeTestRule
            .onNodeWithText("Traductor")
            .performScrollTo()
            .performClick()

        composeTestRule
            .onNodeWithText("Traducir")
            .performScrollTo()
            .performClick()

        composeTestRule
            .onNodeWithText(
                "Ingresa un texto para traducir"
            )
            .performScrollTo()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Limpiar")
            .performScrollTo()
            .performClick()

        composeTestRule
            .onNodeWithText(
                "Ingresa un texto para traducir"
            )
            .assertDoesNotExist()
    }
}
