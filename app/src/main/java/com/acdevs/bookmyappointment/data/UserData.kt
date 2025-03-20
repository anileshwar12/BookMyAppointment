data class Appointment(
    val doctor: String = "",
    val date: String = "",
    val status: String = "",
    val qrCodeUrl: String = ""
)

data class UserProfile(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val profileImage: String = "",
    val appointments: List<Appointment> = emptyList()
)
