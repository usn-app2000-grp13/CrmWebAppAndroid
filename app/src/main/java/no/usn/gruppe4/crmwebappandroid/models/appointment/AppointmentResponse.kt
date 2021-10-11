package no.usn.gruppe4.crmwebappandroid.models.appointment

import com.squareup.moshi.Json
import java.util.*

data class AppointmentResponse(
    val success: Boolean,
    val `data`: List<Appointment>
){
    data class Appointment(
        val __v: Int?,
        val _id: String?,
        val _vendor: String?,
        val comment: String?,
        val createdAt: String?,
        val customers: List<_customer>,
        val date: Date?,
        val duration: Int?,
        val employees: List<_employee>,
        val paymentReceived: Boolean?,
        val ratings: List<Rating>,
        val services: List<_service>,
        val timeindex: Int?,
        val updatedAt: String?
    )
    data class _customer(
        val firstname: String?,
        val lastname: String?,
        val email: String?,
    )
    data class _employee(
        val firstname: String?,
        val lastname: String?,
        val email: String?,
    )
    data class _service(
        val name: String?,
        val description: String?,
    )
    data class Rating(
        val _id: String,
        val rating: Double,
    )
}