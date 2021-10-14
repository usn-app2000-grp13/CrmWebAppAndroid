package no.usn.gruppe4.crmwebappandroid.retrofit

import no.usn.gruppe4.crmwebappandroid.models.IdRequest
import no.usn.gruppe4.crmwebappandroid.models.appointment.AppointmentResponse
import no.usn.gruppe4.crmwebappandroid.models.appointment.OneAppointmentResponse
import no.usn.gruppe4.crmwebappandroid.models.login.LoginRequest
import no.usn.gruppe4.crmwebappandroid.models.login.SessionResponse
import no.usn.gruppe4.crmwebappandroid.models.login.Test
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import no.usn.gruppe4.crmwebappandroid.models.service.ServiceResponse
import retrofit2.http.*
import java.util.*

interface CrmApi {
    @GET("test")
    suspend fun getProperties(): Test

    @GET("login")
    suspend fun login(): SessionResponse

    @POST("login")
    suspend fun loginUser(@Body req: LoginRequest): SessionResponse

    @GET("appointments/{employee}")
    suspend fun getMyAppointments(@Path("employee") _id: String): AppointmentResponse

    @GET("appointments/{employee}/{date}")
    suspend fun getMyAppointments2(@Path("employee") _id: String, @Path("date") date: Date): OneAppointmentResponse

    @GET("appointment/{id}") //appointment/:id
    suspend fun getAppointment(@Path("id") id: String): OneAppointmentResponse

    @GET("appointment")
    suspend fun getAppointments(): AppointmentResponse

    //gjør en kall på /api/service route
    @GET("service")
    suspend fun getServices(): ServiceResponse

    @POST("newService")
    suspend fun newService(@Body req: Service)
/*
    @GET("customer")
    suspend fun getCustomers(): CustomerResponse

    appointment/:id

 */
}