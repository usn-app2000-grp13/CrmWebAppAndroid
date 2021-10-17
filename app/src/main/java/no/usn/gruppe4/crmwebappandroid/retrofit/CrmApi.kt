package no.usn.gruppe4.crmwebappandroid.retrofit

import no.usn.gruppe4.crmwebappandroid.models.IdRequest
import no.usn.gruppe4.crmwebappandroid.models.appointment.Appointment
import no.usn.gruppe4.crmwebappandroid.models.appointment.AppointmentResponse
import no.usn.gruppe4.crmwebappandroid.models.appointment.OneAppointmentResponse
import no.usn.gruppe4.crmwebappandroid.models.customer.CustomerResponse
import no.usn.gruppe4.crmwebappandroid.models.employee.EmployeeResponse
import no.usn.gruppe4.crmwebappandroid.models.login.LoginRequest
import no.usn.gruppe4.crmwebappandroid.models.login.SessionResponse
import no.usn.gruppe4.crmwebappandroid.models.login.Test
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import no.usn.gruppe4.crmwebappandroid.models.service.ServiceResponse
import no.usn.gruppe4.crmwebappandroid.models.todo.Todo
import no.usn.gruppe4.crmwebappandroid.models.todo.TodoResponse
import retrofit2.http.*
import java.util.*

interface CrmApi {
    @GET("test")
    suspend fun getProperties(): Test







    //login routes

    @GET("login")
    suspend fun login(): SessionResponse

    @POST("login")
    suspend fun loginUser(@Body req: LoginRequest): SessionResponse

    //service routes

    //gjør en kall på /api/service route
    @GET("service")
    suspend fun getServices(): ServiceResponse

    @POST("newService")
    suspend fun newService(@Body req: Service)

    @DELETE("service")
    suspend fun deleteService(@Body req: IdRequest)

    @POST("Service")
    suspend fun updateService(@Body req: Service)

    //appointment routes

    @GET("appointments/{employee}/{date}")
    suspend fun getMyAppointments2(@Path("employee") _id: String, @Path("date") date: Date): OneAppointmentResponse

    @GET("appointment")
    suspend fun getAppointments(): OneAppointmentResponse

    @POST("newAppointment")
    suspend fun addAppointment(@Body req: Appointment)

    @PUT("appointment")
    suspend fun updateAppointment(@Body req: Appointment)

    @DELETE("appointment")
    suspend fun deleteAppointment(@Body req: IdRequest)


    //to do routes

    @GET("employeeself/todo")
    suspend fun getTodos(): TodoResponse

    @PUT("employeeself/todo")
    suspend fun addTodo(@Body req: Todo)

    @DELETE("employee/todo")
    suspend fun deleteTodo(@Body req: IdRequest)

    @PUT("employee/todo/completed")
    suspend fun setTodoComplete(@Body req: Todo.SetComplete)

    //employee routes

    @GET("employee")
    suspend fun getEmployees(): EmployeeResponse

    //customer routes

    @GET("customer")
    suspend fun getCustomers(): CustomerResponse


/*


    appointment/:id

 */
}