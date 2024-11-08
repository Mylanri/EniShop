package com.example.enishop.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Thème clair
val LightPrimary = Color(0xFF6200EE)
val LightPrimaryVariant = Color(0xFF3700B3)
val LightSecondary = Color(0xFF03DAC6)
val LightBackground = Color(0xFFFFFFFF)
val LightSurface = Color(0xFFFAFAFA)
val LightOnPrimary = Color(0xFFFFFFFF)
val LightOnSecondary = Color(0xFF000000)
val LightOnSurface = Color(0xFF000000)

// Thème sombre
val DarkPrimary = Color(0xFFBB86FC)
val DarkPrimaryVariant = Color(0xFF3700B3)
val DarkSecondary = Color(0xFF03DAC6)
val DarkBackground = Color(0xFF121212)
val DarkSurface = Color(0xFF121212)
val DarkOnPrimary = Color(0xFF000000)
val DarkOnSecondary = Color(0xFF000000)
val DarkOnSurface = Color(0xFFFFFFFF)

// Définition des couleurs pour le thème clair et sombre
private val LightColors = lightColorScheme(
    primary = LightPrimary,
    primaryContainer = LightPrimaryVariant,
    secondary = LightSecondary,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = LightOnPrimary,
    onSecondary = LightOnSecondary,
    onSurface = LightOnSurface,
    onBackground = LightOnSurface
)

private val DarkColors = darkColorScheme(
    primary = DarkPrimary,
    primaryContainer = DarkPrimaryVariant,
    secondary = DarkSecondary,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = DarkOnPrimary,
    onSecondary = DarkOnSecondary,
    onSurface = DarkOnSurface,
    onBackground = DarkOnSurface
)

@Composable
fun EniShopTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
