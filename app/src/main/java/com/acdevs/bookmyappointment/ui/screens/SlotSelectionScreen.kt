package com.acdevs.bookmyappointment.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acdevs.bookmyappointment.ui.components.SlotItem
import com.acdevs.bookmyappointment.viewmodel.AppointmentViewModel
import java.time.format.DateTimeFormatter

@Composable
fun SlotSelectionScreen(
    viewModel: AppointmentViewModel,
    doctorName: String,
    onSlotSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Available Slots for $doctorName",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Date selection
        Text(
            text = "Selected Date: ${viewModel.selectedDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))}",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Show loading state
        if (viewModel.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        // Show error if any
        viewModel.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Slots list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.slots) { slot ->
                SlotItem(
                    time = slot.time,
                    isBooked = slot.isBooked,
                    onClick = {
                        if (!slot.isBooked) {
                            onSlotSelected(slot.time)
                        }
                    }
                )
            }
        }
    }
}