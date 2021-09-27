package no.usn.gruppe4.crmwebappandroid.models.appointment

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

data class Appointment(
    @StringRes val time: Int,
    @StringRes val customer: Int,
    @StringRes val service: Int
) {
}