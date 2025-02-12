package com.acdevs.bookmyappointment.data

import kotlinx.coroutines.flow.Flow

class AppointmentRepository(private val appointmentDao: AppointmentDao) {
    fun getAllAppointments(): Flow<List<Appointments>> = appointmentDao.getAllAppointments()
    
    suspend fun insertAppointment(appointment: Appointments) {
        appointmentDao.insertAppointment(appointment)
    }
    
    suspend fun deleteAppointment(appointment: Appointments) {
        appointmentDao.deleteAppointment(appointment)
    }
} 