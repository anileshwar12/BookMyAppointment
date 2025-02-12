package com.acdevs.bookmyappointment.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromAppointmentStatus(status: AppointmentStatus): String {
        return status.name
    }

    @TypeConverter
    fun toAppointmentStatus(status: String): AppointmentStatus {
        return AppointmentStatus.valueOf(status)
    }
} 