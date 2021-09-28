package no.usn.gruppe4.crmwebappandroid.models.employee

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Employee (
    val firstname: String,
    val lastname: String,
    val phone: String,
): Parcelable {
}
