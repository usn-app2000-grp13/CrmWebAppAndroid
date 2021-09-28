package no.usn.gruppe4.crmwebappandroid.models.appointment

import android.os.Parcelable
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Appointment(
    val date: String,
    val time: String,
    val comment: String,
    val duration: String,
    val customer: String,
    val service: String,
    val employee: String
) : Parcelable {
    override fun toString(): String {
        return "date: $date, time $time, comment: $comment, duration: $duration, customer: $customer, service: $service, employee: $employee"
    }
}

/*
date, comment, duration, Services, Customers, Employees
 */