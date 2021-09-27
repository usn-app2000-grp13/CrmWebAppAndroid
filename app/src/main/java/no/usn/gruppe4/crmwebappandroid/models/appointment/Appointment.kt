package no.usn.gruppe4.crmwebappandroid.models.appointment

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

data class Appointment(
    val time: String,
    val customer: String,
    val service: String
) {
}