package no.usn.gruppe4.crmwebappandroid.models.customer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Customer(
    val id: String?,
    val firstname: String?,
    val lastname: String?,
    val email: String?,
    val phone: String?,
): Parcelable {
}