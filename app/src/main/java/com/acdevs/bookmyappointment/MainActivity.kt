package com.acdevs.bookmyappointment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.acdevs.bookmyappointment.ui.screens.DoctorAppointmentApp
import com.acdevs.bookmyappointment.ui.theme.BookMyAppointmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookMyAppointmentTheme {
                val navController = rememberNavController()
                DoctorAppointmentApp(navController)
            }
        }
    }
}
