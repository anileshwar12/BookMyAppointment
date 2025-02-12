package com.acdevs.bookmyappointment.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SlotItem(time: String, isBooked: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = if (isBooked) Color.Red else Color.Green),
        modifier = Modifier.fillMaxWidth().padding(4.dp),
        enabled = !isBooked
    ) {
        Text(time)
    }
}
