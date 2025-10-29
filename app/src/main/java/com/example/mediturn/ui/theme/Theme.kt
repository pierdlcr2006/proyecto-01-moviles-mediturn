package com.example.mediturn.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = TurquoisePrimaryDark,
    onPrimary = Color(0xFF003640),
    primaryContainer = TurquoiseDarkVariant,
    onPrimaryContainer = Color(0xFF003640),
    
    secondary = TurquoiseDarkVariant,
    onSecondary = Color(0xFF00363F),
    
    tertiary = TurquoisePrimaryDark,
    onTertiary = Color(0xFF003640),
    
    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    
    surfaceVariant = CardDark,
    onSurfaceVariant = TextSecondaryDark,
    
    outline = DividerDark,
    outlineVariant = DividerDark,
    
    error = Color(0xFFFF6B6B),
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = TurquoisePrimary,
    onPrimary = Color.White,
    primaryContainer = TurquoiseLight,
    onPrimaryContainer = TurquoiseDark,
    
    secondary = TurquoiseDark,
    onSecondary = Color.White,
    
    tertiary = TurquoisePrimary,
    onTertiary = Color.White,
    
    background = BackgroundLight,
    onBackground = TextPrimary,
    
    surface = SurfaceLight,
    onSurface = TextPrimary,
    
    surfaceVariant = Color(0xFFF5F5F5),
    onSurfaceVariant = TextSecondary,
    
    outline = Color(0xFFE0E0E0),
    outlineVariant = Color(0xFFEEEEEE),
    
    error = Color(0xFFD32F2F),
    onError = Color.White
)

@Composable
fun MediTurnTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Usar siempre nuestros colores personalizados (sin dynamic color)
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    // Configurar la barra de estado
    val view = androidx.compose.ui.platform.LocalView.current
    if (!view.isInEditMode) {
        androidx.compose.runtime.SideEffect {
            val window = (view.context as? Activity)?.window
            window?.let {
                // Configurar color de la barra de estado según el tema
                it.statusBarColor = if (darkTheme) {
                    android.graphics.Color.parseColor("#121212") // BackgroundDark
                } else {
                    android.graphics.Color.parseColor("#FAFAFA") // BackgroundLight
                }
                
                // Hacer que los iconos de la barra de estado sean claros u oscuros
                androidx.core.view.WindowCompat.getInsetsController(it, view)
                    .isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}