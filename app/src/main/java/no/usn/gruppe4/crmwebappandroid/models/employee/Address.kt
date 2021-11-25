package no.usn.gruppe4.crmwebappandroid.models.employee

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(var street: String?,
                   var streetNumber: String?,
                   var postCode: String?,
                   var postArea: String?,
                   var apartment: String?,): Parcelable {
}