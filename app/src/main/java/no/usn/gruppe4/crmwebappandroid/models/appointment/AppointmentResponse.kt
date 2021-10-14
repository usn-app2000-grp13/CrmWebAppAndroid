package no.usn.gruppe4.crmwebappandroid.models.appointment

import com.squareup.moshi.Json
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import java.util.*

data class AppointmentResponse(
    val success: Boolean,
    val data : List<Appointment>
){
    data class Appointment(
        val __v: Int?,
        val _id: String?,
        val _vendor: String?,
        val comment: String?,
        val createdAt: String?,
        val customers: List<Customer>,
        val date: Date?,
        val duration: Int?,
        val employees: List<_employee>,
        val paymentReceived: Boolean?,
        val ratings: List<Rating>,
        val services: List<Service>,
        val timeindex: Int?,
        val updatedAt: String?
    )

    data class _employee(
        val _id: String?,
        val firstname: String?,
        val lastname: String?,
        val email: String?,
    )

    data class Rating(
        val _id: String,
        val rating: Double,
    )
}