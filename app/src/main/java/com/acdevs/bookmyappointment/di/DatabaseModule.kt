package com.acdevs.bookmyappointment.di

import android.content.Context
import com.acdevs.bookmyappointment.data.AppointmentDao
import com.acdevs.bookmyappointment.data.AppointmentDatabase
import com.acdevs.bookmyappointment.data.AppointmentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppointmentDatabase {
        return AppointmentDatabase.getDatabase(context)
    }
    
    @Provides
    fun provideAppointmentDao(database: AppointmentDatabase): AppointmentDao {
        return database.appointmentDao()
    }
    
    @Provides
    @Singleton
    fun provideRepository(appointmentDao: AppointmentDao): AppointmentRepository {
        return AppointmentRepository(appointmentDao)
    }
} 