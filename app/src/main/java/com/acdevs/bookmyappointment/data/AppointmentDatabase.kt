package com.acdevs.bookmyappointment.data

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.acdevs.bookmyappointment.data.Converters

@Database(entities = [Appointments::class], version = 1)
@TypeConverters(com.acdevs.bookmyappointment.data.Converters::class)
abstract class AppointmentDatabase : RoomDatabase() {
    abstract fun appointmentDao(): AppointmentDao

    companion object {
        @Volatile
        private var INSTANCE: AppointmentDatabase? = null

        fun getDatabase(context: Context): AppointmentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppointmentDatabase::class.java,
                    "appointment_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 