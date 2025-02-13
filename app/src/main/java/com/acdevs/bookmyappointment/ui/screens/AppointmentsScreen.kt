package com.acdevs.bookmyappointment.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.acdevs.bookmyappointment.data.AppointmentStatus
import com.acdevs.bookmyappointment.data.Appointments
import com.acdevs.bookmyappointment.viewmodel.AppointmentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentsScreen(viewModel: AppointmentViewModel, navController: NavHostController) {
    val appointments by viewModel.appointments.collectAsState(initial = emptyList())

    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("My Appointments") },
                        navigationIcon = {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                        }
                )
            }
    ) { paddingValues ->
        if (appointments.isEmpty()) {
            EmptyAppointmentsMessage(modifier = Modifier.padding(paddingValues))
        } else {
            LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(appointments) { appointment ->
                    AppointmentCard(
                            appointment = appointment,
                            onCancelClick = { viewModel.cancelAppointment(appointment) }
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyAppointmentsMessage(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
            Icon(
                    Icons.Outlined.Schedule,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("No appointments yet", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                    "Book your first appointment",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentCard(appointment: Appointments, onCancelClick: () -> Unit) {
    Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = appointment.doctorName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                AppointmentStatusChip(status = appointment.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = appointment.category, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                            text = "Date",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(text = appointment.date, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
                Column {
                    Text(
                            text = "Time",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(text = appointment.time, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
                if (appointment.status == AppointmentStatus.SCHEDULED) {
                    TextButton(
                            onClick = onCancelClick,
                            colors =
                                    ButtonDefaults.textButtonColors(
                                            contentColor = MaterialTheme.colorScheme.error
                                    )
                    ) {
                        Icon(
                                Icons.Outlined.Cancel,
                                contentDescription = "Cancel",
                                modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Cancel")
                    }
                }
            }
        }
    }
}

@Composable
fun AppointmentStatusChip(status: AppointmentStatus) {
    val (backgroundColor, contentColor, icon) =
            when (status) {
                AppointmentStatus.SCHEDULED ->
                        Triple(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.onPrimaryContainer,
                                Icons.Outlined.Schedule
                        )
                AppointmentStatus.COMPLETED ->
                        Triple(
                                MaterialTheme.colorScheme.secondaryContainer,
                                MaterialTheme.colorScheme.onSecondaryContainer,
                                Icons.Default.CheckCircle
                        )
                AppointmentStatus.CANCELLED ->
                        Triple(
                                MaterialTheme.colorScheme.errorContainer,
                                MaterialTheme.colorScheme.onErrorContainer,
                                Icons.Outlined.Cancel
                        )
            }

    Surface(color = backgroundColor, shape = MaterialTheme.shapes.small) {
        Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = contentColor
            )
            Text(
                    text = status.name,
                    color = contentColor,
                    style = MaterialTheme.typography.labelMedium
            )
        }
    }
}
