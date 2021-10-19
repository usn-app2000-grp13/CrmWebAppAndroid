package no.usn.gruppe4.crmwebappandroid.models.employee

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Todo(
    val _id: String,
    val completed: Boolean,
    val date: String,
    val description: String
): Parcelable {
}
