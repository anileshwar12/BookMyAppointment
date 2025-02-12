package com.acdevs.bookmyappointment.data

data class Slot(
    val id: Long = 0,
    val time: String,
    val isBooked: Boolean,
    val date: String,
    val doctorId: Long? = null
)