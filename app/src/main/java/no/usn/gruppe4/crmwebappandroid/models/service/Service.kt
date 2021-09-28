package no.usn.gruppe4.crmwebappandroid.models.service

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Service(
    val title: String,
    val description: String,
    val duration: String,
    val price: String
): Parcelable {
}