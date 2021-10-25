package no.usn.gruppe4.crmwebappandroid.models.customer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Customer(
    val _id: String?,
    val firstname: String?,
    val lastname: String?,
    val email: String?,
    val phone: String?,
): Parcelable {
    override fun toString(): String {
        return "$firstname $lastname"
    }
}