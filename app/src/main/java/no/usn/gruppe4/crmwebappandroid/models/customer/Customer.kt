package no.usn.gruppe4.crmwebappandroid.models.customer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import no.usn.gruppe4.crmwebappandroid.models.service.Service

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
    fun checkId(customers: List<Customer>): Boolean{
        for (i in customers){
            if (this._id.equals(i._id)) return true
        }
        return false
    }

}