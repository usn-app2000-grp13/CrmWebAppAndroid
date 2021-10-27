package no.usn.gruppe4.crmwebappandroid.models.service

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class Service(
    val _id: String?,
    var description: String?,
    var duration: Int?,
    var name: String?,
    var price: String?,
) : Parcelable