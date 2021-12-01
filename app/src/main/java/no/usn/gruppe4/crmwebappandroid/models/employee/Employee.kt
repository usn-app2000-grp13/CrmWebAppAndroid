package no.usn.gruppe4.crmwebappandroid.models.employee

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer


@Parcelize
data class Employee (
    val _id: String?,
    val _vendor: String?,
    val active: Boolean?,
    val createdAt: String?,
    var email: String?,
    var firstname: String?,
    var lastname: String?,
    val level: Int?,
    var phone: String?,
    val updatedAt: String?,
    var address: Address?,
    val password: String?,
    val __v: Int?): Parcelable {
    override fun toString(): String {
        return "$firstname $lastname"
    }
    fun checkId(employees: List<Employee>): Boolean{
        for (i in employees){
            if (this._id.equals(i._id)) return true
        }
        return false
    }
}
