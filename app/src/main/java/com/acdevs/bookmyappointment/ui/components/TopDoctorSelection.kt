package com.acdevs.bookmyappointment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

data class Doctor(
    val name: String,
    val specialty: String,
    val rating: Float,
    val experience: Int
)

@Composable
fun TopDoctorsSection(navController: NavHostController) {
    val doctors = listOf(
        Doctor("Dr. Chopper", "Cardiologist", 4.5f, 15),
        Doctor("Dr. Zoro", "Pediatrician", 4.8f, 12),
        Doctor("Dr. Luffy", "Neurologist", 4.7f, 10)
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "Top Doctors",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(doctors) { doctor ->
                DoctorCard(
                    doctor = doctor,
                    onClick = { navController.navigate("slotSelection/${doctor.name}") }
                )
            }
        }
    }
}

@Composable
fun DoctorCard(doctor: Doctor, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = doctor.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = doctor.specialty,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Color(0xFFFFB800),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = doctor.rating.toString(),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "${doctor.experience} years exp",
                    fontSize = 14.sp
                )
            }
        }
    }
}