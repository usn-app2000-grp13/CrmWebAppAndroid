package no.usn.gruppe4.crmwebappandroid.retrofit

import no.usn.gruppe4.crmwebappandroid.models.IdRequest
import no.usn.gruppe4.crmwebappandroid.models.appointment.AppointmentResponse
import no.usn.gruppe4.crmwebappandroid.models.login.LoginRequest
import no.usn.gruppe4.crmwebappandroid.models.login.SessionResponse
import no.usn.gruppe4.crmwebappandroid.models.login.Test
import retrofit2.http.*

interface CrmApi {
    @GET("test")
    suspend fun getProperties(): Test

    @GET("login")
    suspend fun login(): SessionResponse

    @POST("login")
    suspend fun loginUser(@Body req: LoginRequest): SessionResponse

    @GET("appointments/:{employee}")
    suspend fun getAppointments(@Path("employee") _id: String): AppointmentResponse

    @GET("appointment/:{id}")
    suspend fun getAppointment(@Path("id") id: IdRequest): AppointmentResponse

    @GET("appointment")
    suspend fun getAppointments(): AppointmentResponse
/*
    @GET("customer")
    suspend fun getCustomers(): CustomerResponse

    appointment/:id

 */
}