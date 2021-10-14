package no.usn.gruppe4.crmwebappandroid.models.appointment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee
import no.usn.gruppe4.crmwebappandroid.models.service.Service

@Parcelize
data class OneAppointmentResponse(
    val success: Boolean,
    val data: List<Appointment>?,
): Parcelable {
    @Parcelize
    data class Service(
        val _id: String,
        val _service: no.usn.gruppe4.crmwebappandroid.models.service.Service
    ): Parcelable
    @Parcelize
    data class Customer(
        val _id: String,
        val _customer: no.usn.gruppe4.crmwebappandroid.models.customer.Customer
    ): Parcelable
    @Parcelize
    data class Employee(
        val _id: String,
        val serviceObject: no.usn.gruppe4.crmwebappandroid.models.employee.Employee
    ): Parcelable
}