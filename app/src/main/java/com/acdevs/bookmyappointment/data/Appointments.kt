package com.acdevs.bookmyappointment.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appointments")
data class Appointments(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val doctorName: String,
    val category: String,
    val time: String,
    val date: String,
    val patientName: String,
    val status: AppointmentStatus = AppointmentStatus.SCHEDULED
)

enum class AppointmentStatus {
    SCHEDULED,
    COMPLETED,
    CANCELLED
}