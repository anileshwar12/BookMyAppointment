package com.acdevs.bookmyappointment.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

//private val LightColorScheme = lightColorScheme(
//    primary = Color(0xFFF57C00), // Deep Orange
//    secondary = Color(0xFFFFA726), // Light Orange
//    background = Color(0xFFFFFFFF), // White
//    surface = Color(0xFFFFF3E0), // Light Cream
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onBackground = Color.Black,
//    onSurface = Color.Black
//)

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF8D6E63),
    secondary = Color(0xFFD7CCC8),
    background = Color(0xFFF5F5F5),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onBackground = Color(0xFF3E2723)
)





@Composable
fun BookMyAppointmentTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}