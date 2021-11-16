package no.usn.gruppe4.crmwebappandroid.retrofit

import no.usn.gruppe4.crmwebappandroid.models.IdRequest
import no.usn.gruppe4.crmwebappandroid.models.appointment.Appointment
import no.usn.gruppe4.crmwebappandroid.models.appointment.OneAppointmentResponse
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.models.customer.CustomerResponse
import no.usn.gruppe4.crmwebappandroid.models.customer.CustomerResponseNoArray
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee
import no.usn.gruppe4.crmwebappandroid.models.employee.EmployeeResponse
import no.usn.gruppe4.crmwebappandroid.models.employee.EmployeeResponseNoArray
import no.usn.gruppe4.crmwebappandroid.models.employee.EmployeeViewModel
import no.usn.gruppe4.crmwebappandroid.models.login.LoginRequest
import no.usn.gruppe4.crmwebappandroid.models.login.ResetPasswordRequest
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

    @POST("resetPassword")
    suspend fun resetPassword(@Body req: ResetPasswordRequest)

    @GET("logout")
    suspend fun logoutUser()

    //service routes

    //gjør en kall på /api/service route
    @GET("service")
    suspend fun getServices(): ServiceResponse

    @POST("newService")
    suspend fun newService(@Body req: Service)

    @HTTP(method = "DELETE",path = "service", hasBody = true)
    suspend fun deleteService(@Body req: IdRequest)

    @PUT("service")
    suspend fun updateService(@Body req: Service)

    //appointment routes

    @GET("appointments/{employee}/{date}")
    suspend fun getMyAppointments2(@Path("employee") _id: String, @Path("date") date: Date): OneAppointmentResponse

    @GET("appointment")
    suspend fun getAppointments(): OneAppointmentResponse

    @POST("newAppointment")
    suspend fun addAppointment(@Body req: Appointment.newAppointment)

    @PUT("appointment")
    suspend fun updateAppointment(@Body req: Appointment)

    @HTTP(method = "DELETE",path = "appointment", hasBody = true)
    suspend fun deleteAppointment(@Body req: IdRequest)


    //to do routes

    @GET("employeeself/todo")
    suspend fun getTodos(): TodoResponse

    @PUT("employeeself/todo")
    suspend fun addTodo(@Body req: Todo.addTodo)

    @HTTP(method = "DELETE",path = "employee/todo", hasBody = true)
    suspend fun deleteTodo(@Body req: Todo.deleteTodo)

    @PUT("employee/todo/completed")
    suspend fun setTodoComplete(@Body req: Todo.SetComplete)

    //employee routes

    @GET("employee")
    suspend fun getEmployees(): EmployeeResponse

    @GET("employee/{id}")
    suspend fun getOneEmployee(@Path("id") _id: String): EmployeeResponse.SingleEmployeeResponse

    @POST("newEmployee")
    suspend fun postEmployees(@Body req: Employee): EmployeeResponseNoArray

    //Retrofit seems to think body for delete is not a thing,
    //it is however so on our api, so need to write the delete somewhat differently.
    //https://stackoverflow.com/questions/37942474/delete-method-is-not-supportingnon-body-http-method-cannot-contain-body-or-t
    @HTTP(method = "DELETE",path = "employee", hasBody = true)
    suspend fun deleteEmployees(@Body req: EmployeeViewModel.DeleteEmployee): EmployeeResponseNoArray

    @PUT("employee")
    suspend fun putEmployees(@Body req: Employee): EmployeeResponseNoArray


    //customer routes

    @GET("customer")
    suspend fun getCustomers(): CustomerResponse

    @POST("newCustomer")
    suspend fun postCustomer(@Body req: Customer): CustomerResponseNoArray


/*


    appointment/:id

 */
}