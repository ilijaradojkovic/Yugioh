package com.example.yugioh.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple200,
    secondary = Purple200
)

private val LightColorPalette = lightColors(
    primary = Purple200,
    primaryVariant = Purple200,
    secondary = Purple200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun YugiohTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val systemUiController = rememberSystemUiController()
   systemUiController.setStatusBarColor(redColorYugioh)

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}


@Composable
fun Colors.IconsColor():Color{
    return if(isLight) Color.Red else Color.Black
}

@Composable
fun Color.CircleSelectionColor():Color{
    return if(MaterialTheme.colors.isLight) Color.Red else Color.White
}

@Composable
fun Colors.ProgressBarColor():Color{
    return if(MaterialTheme.colors.isLight) Color.DarkGray else Color.Gray
}

@Composable
fun Colors.TextColor():Color{
    return if(isLight) Color.Black else Color.White
}
@Composable
fun Colors.BorderColor():Color{
    return if(isLight) Color.DarkGray else Color.White
}

