package com.acdevs.bookmyappointment.ui

import UserProfile
import coil.compose.AsyncImage
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
//import com.acdevs.bookmyappointment.model.UserProfile

@Composable
fun UserProfileScreen(user: UserProfile) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Image(
            painter = rememberImagePainter(user.profileImage),
            contentDescription = "Profile Image",
            modifier = Modifier.size(120.dp).align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )
        Text(user.name, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.align(Alignment.CenterHorizontally))
        Text(user.email, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(user.appointments) { appointment ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Doctor: ${appointment.doctor}", style = MaterialTheme.typography.bodyLarge)
                        Text("Date: ${appointment.date}", style = MaterialTheme.typography.bodyLarge)
                        Text("Status: ${appointment.status}", style = MaterialTheme.typography.bodyLarge)
                        AsyncImage(
                            model = appointment.qrCodeUrl,
                            contentDescription = "QR Code",
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}
