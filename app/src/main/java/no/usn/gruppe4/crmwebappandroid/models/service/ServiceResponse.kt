package no.usn.gruppe4.crmwebappandroid.models.service

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ServiceResponse(
    val success: Boolean,
    val `data`: List<Service>
    ) : Parcelable {

}