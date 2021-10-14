package no.usn.gruppe4.crmwebappandroid.models.appointment

import android.os.Parcelable
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import java.util.*

@Parcelize
data class Appointment(
    val __v: Int?,
    val _id: String?,
    val _vendor: String?,
    val comment: String?,
    val createdAt: String?,
    val customers: List<OneAppointmentResponse.Customer>,
    val date: Date?,
    val duration: Int?,
    //val employees: List<AppointmentResponse._employee>,
    val paymentReceived: Boolean?,
    //val ratings: List<AppointmentResponse.Rating>,
    val services: List<OneAppointmentResponse.Service>,
    val timeindex: Int?,
    val updatedAt: String?
) : Parcelable {

}

/*
date, comment, duration, Services, Customers, Employees
 */