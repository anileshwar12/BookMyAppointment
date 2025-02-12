package com.acdevs.bookmyappointment.data
import java.util.UUID

data class Appointment(
    val id: String = UUID.randomUUID().toString(),
    val doctorName: String,
    val category: String,
    val date: String,
    val time: String
)
