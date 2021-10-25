package no.usn.gruppe4.crmwebappandroid.models.service

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class Service(
    val _id: String?,
    val description: String?,
    val duration: Int?,
    val name: String?,
    val price: String?,
) : Parcelable{
    override fun toString(): String {
        return "$name"
    }
}