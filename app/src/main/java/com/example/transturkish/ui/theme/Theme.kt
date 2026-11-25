package com.example.transturkish.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Esquema del modo oscuro
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

// Nuevo esquema de TransTurkish
private val LightColorScheme = lightColorScheme(
    primary = MoradoPrincipal,
    onPrimary = Blanco,

    secondary = MoradoSecundario,
    onSecondary = Blanco,

    tertiary = MoradoClaro,

    background = Blanco,
    onBackground = TextoOscuro,

    surface = Blanco,
    onSurface = TextoOscuro,

    primaryContainer = Lavanda,
    onPrimaryContainer = TextoOscuro,

    surfaceVariant = Lavanda,
    onSurfaceVariant = TextoOscuro
)

@Composable
fun Guia9Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current

            if (darkTheme)
                dynamicDarkColorScheme(context)
            else
                dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
