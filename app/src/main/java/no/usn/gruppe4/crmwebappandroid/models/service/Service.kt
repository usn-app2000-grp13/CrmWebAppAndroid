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
) : Parcelable {
    override fun toString(): String {
        return "$name"
    }
    fun checkId(services: List<Service>): Boolean{
        for (i in services){
            if (this._id.equals(i._id)) return true
        }
        return false
    }
}