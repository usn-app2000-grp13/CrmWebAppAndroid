package no.usn.gruppe4.crmwebappandroid.models.appointment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OneAppointmentResponse(
    val success: Boolean,
    val data: List<Appointment>?,
): Parcelable {
    @Parcelize
    data class Service(
        val _id: String?,
        var _service: no.usn.gruppe4.crmwebappandroid.models.service.Service?
    ): Parcelable
    @Parcelize
    data class Customer(
        val _id: String?,
        var _customer: no.usn.gruppe4.crmwebappandroid.models.customer.Customer?
    ): Parcelable
    @Parcelize
    data class Employee(
        val _id: String?,
        var _employee: no.usn.gruppe4.crmwebappandroid.models.employee.Employee?
    ): Parcelable
    @Parcelize
    data class Rating(
        val _id: String,
        val rating: Double,
    ):Parcelable
}