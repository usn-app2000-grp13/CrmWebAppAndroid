package no.usn.gruppe4.crmwebappandroid.models.login

import kotlinx.android.parcel.Parcelize


data class SessionResponse(
    val `data`: Data,
    val success: Boolean
){
    data class Data(
        val active: Boolean,
        val firstname: String,
        val id: String,
        val lastname: String,
        val level: Int,
        val vendor: String
    )
}