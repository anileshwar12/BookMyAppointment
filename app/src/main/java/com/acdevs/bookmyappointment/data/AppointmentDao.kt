package com.acdevs.bookmyappointment.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {
    @Query("SELECT * FROM appointments ORDER BY date DESC")
    fun getAllAppointments(): Flow<List<Appointments>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: Appointments)
    
    @Delete
    suspend fun deleteAppointment(appointment: Appointments)
} 