package com.acdevs.bookmyappointment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.acdevs.bookmyappointment.data.Appointment
import com.acdevs.bookmyappointment.data.Slot
import com.acdevs.bookmyappointment.data.AppointmentRepository
import com.acdevs.bookmyappointment.data.Appointments
import com.acdevs.bookmyappointment.data.AppointmentStatus
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val repository: AppointmentRepository
) : ViewModel() {

    private val _slots = mutableStateListOf<Slot>()
    val slots: List<Slot> = _slots

    private val _appointments = MutableStateFlow<List<Appointments>>(emptyList())
    val appointments: StateFlow<List<Appointments>> = _appointments

    var selectedDate by mutableStateOf(LocalDate.now())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadSlots(selectedDate)
        loadAppointments()
    }

    fun updateSelectedDate(date: LocalDate) {
        selectedDate = date
        loadSlots(date)
    }

    private fun loadSlots(date: LocalDate) {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                // Clear existing slots
                _slots.clear()

                // Generate slots for the selected date
                val formattedDate = date.format(DateTimeFormatter.ISO_DATE)
                val availableSlots = generateTimeSlots(formattedDate)
                _slots.addAll(availableSlots)

            } catch (e: Exception) {
                errorMessage = "Failed to load slots: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    private fun generateTimeSlots(date: String): List<Slot> {
        // Generate slots from 9 AM to 5 PM with 30-minute intervals
        val slots = mutableListOf<Slot>()
        var hour = 9
        var minute = 0

        while (hour < 17) {
            val timeString = String.format("%02d:%02d", hour, minute)
            slots.add(
                Slot(
                    time = timeString,
                    isBooked = false,
                    date = date
                )
            )

            minute += 30
            if (minute >= 60) {
                minute = 0
                hour++
            }
        }
        return slots
    }

    private fun loadAppointments() {
        viewModelScope.launch {
            repository.getAllAppointments().collect { appointmentsList ->
                _appointments.value = appointmentsList
            }
        }
    }

    fun bookAppointment(
        doctorName: String,
        category: String,
        time: String,
        patientName: String
    ) {
        viewModelScope.launch {
            try {
                errorMessage = null
                
                // Create new appointment
                val appointment = Appointments(
                    doctorName = doctorName,
                    category = category,
                    time = time,
                    date = selectedDate.format(DateTimeFormatter.ISO_DATE),
                    patientName = patientName,
                    status = AppointmentStatus.SCHEDULED
                )

                // Save to Room database
                repository.insertAppointment(appointment)

                // Mark slot as booked
                val slotIndex = _slots.indexOfFirst { it.time == time }
                if (slotIndex != -1) {
                    _slots[slotIndex] = _slots[slotIndex].copy(isBooked = true)
                }

            } catch (e: Exception) {
                errorMessage = "Failed to book appointment: ${e.message}"
            }
        }
    }

    fun cancelAppointment(appointment: Appointments) {
        viewModelScope.launch {
            try {
                repository.deleteAppointment(appointment)
                
                // Mark slot as available
                val slotIndex = _slots.indexOfFirst {
                    it.time == appointment.time && 
                    it.date == appointment.date
                }
                if (slotIndex != -1) {
                    _slots[slotIndex] = _slots[slotIndex].copy(isBooked = false)
                }
            } catch (e: Exception) {
                errorMessage = "Failed to cancel appointment: ${e.message}"
            }
        }
    }
}
