package com.acdevs.bookmyappointment.repository

import Appointment
import UserProfile
import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()
    private val storageRef = FirebaseStorage.getInstance().reference

    fun saveUserToFirestore(user: UserProfile, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("users").document(user.email)
            .set(user)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun uploadQRCode(qrBitmap: Bitmap, appointmentId: String, callback: (String) -> Unit) {
        val qrRef = storageRef.child("qr_codes/$appointmentId.png")
        val baos = ByteArrayOutputStream()
        qrBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()

        qrRef.putBytes(data)
            .addOnSuccessListener {
                qrRef.downloadUrl.addOnSuccessListener { uri ->
                    callback(uri.toString())
                }
            }
            .addOnFailureListener { Log.e("Firebase", "QR Upload Failed", it) }
    }

    fun bookAppointment(userEmail: String, appointment: Appointment, qrBitmap: Bitmap, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        uploadQRCode(qrBitmap, "${appointment.doctor}-${appointment.date}") { qrUrl ->
            val updatedAppointment = appointment.copy(qrCodeUrl = qrUrl)
            db.collection("users").document(userEmail)
                .update("appointments", FieldValue.arrayUnion(updatedAppointment))
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onFailure(it) }
        }
    }
}
