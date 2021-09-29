package no.usn.gruppe4.crmwebappandroid.models.employee

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Employee (
    val firstname: String,
    val lastname: String,
    val phone: String,
    val email: String,
    val level: String,
    val street: String,
    val streetNumber: String,
    val postCode: String,
    val postArea: String,
    val apartment: String,

): Parcelable {
}
