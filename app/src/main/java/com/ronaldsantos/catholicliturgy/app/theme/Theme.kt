package com.ronaldsantos.catholicliturgy.app.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColors = lightColorScheme(
    primary = Purple500,
    onPrimary = Color.White,
    primaryContainer = Purple100,
    onPrimaryContainer = Purple500,
    background = WhiteBG,
    onBackground = DarkText,
    surface = SurfaceWhite,
    onSurface = DarkText,
    secondary = GrayText
)

private val DarkColors = darkColorScheme(
    primary = Purple100,
    onPrimary = Purple500,
    primaryContainer = Purple500,
    onPrimaryContainer = Color.White,
    background = BackgroundDark,
    onBackground = TextOnDark,
    surface = SurfaceDark,
    onSurface = TextOnDark,
    secondary = GrayText
)

val CatholicLiturgyColors: ColorScheme
    @Composable
    get() = MaterialTheme.colorScheme

val CatholicLiturgyTypography: Typography
    @Composable
    get() = MaterialTheme.typography

@Suppress("unused")
val CatholicLiturgyShapes: Shapes
    @Composable
    get() = MaterialTheme.shapes

@Composable
fun CatholicLiturgyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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
        typography = TheOneTypography,
        shapes = TheOneShapes,
        content = content
    )
}