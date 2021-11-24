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
    val email: String?,
    val firstname: String?,
    val lastname: String?,
    val level: Int?,
    val phone: String?,
    val updatedAt: String?,
    val address: Address?,
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
