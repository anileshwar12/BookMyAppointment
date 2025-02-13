package com.acdevs.bookmyappointment.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acdevs.bookmyappointment.data.Slot
import com.acdevs.bookmyappointment.viewmodel.AppointmentViewModel
import java.time.format.DateTimeFormatter

@Composable
fun BookingScreen(
    viewModel: AppointmentViewModel,
    doctorName: String,
    onBookingComplete: () -> Unit
) {
    var selectedSlot by remember { mutableStateOf<Slot?>(null) }
    var showBookingDialog by remember { mutableStateOf(false) }
    var patientName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Book Appointment with $doctorName",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            text = "Selected Date: ${viewModel.selectedDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))}",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (viewModel.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(viewModel.slots) { slot ->
                TimeSlotCard(
                    slot = slot,
                    isSelected = selectedSlot == slot,
                    onClick = {
                        if (!slot.isBooked) {
                            selectedSlot = slot
                            showBookingDialog = true
                        }
                    }
                )
            }
        }
    }

    if (showBookingDialog) {
        BookingDialog(
            onDismiss = { showBookingDialog = false },
            onConfirm = { name ->
                selectedSlot?.let { slot ->
                    viewModel.bookAppointment(
                        doctorName = doctorName,
                        category = "General",
                        time = slot.time,
                        patientName = name
                    )
                }
                onBookingComplete()
            }
        )
    }
}

@Composable
fun TimeSlotCard(
    slot: Slot,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                slot.isBooked -> MaterialTheme.colorScheme.errorContainer
                isSelected -> MaterialTheme.colorScheme.primaryContainer
                else -> MaterialTheme.colorScheme.surface
            }
        ),
        onClick = onClick,
        enabled = !slot.isBooked
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = slot.time,
                style = MaterialTheme.typography.bodyLarge,
                color = if (slot.isBooked) 
                    MaterialTheme.colorScheme.error 
                else 
                    MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun BookingDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var patientName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Book Appointment") },
        text = {
            TextField(
                value = patientName,
                onValueChange = { patientName = it },
                label = { Text("Patient Name") }
            )
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(patientName) },
                enabled = patientName.isNotBlank()
            ) {
                Text("Book")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
} 