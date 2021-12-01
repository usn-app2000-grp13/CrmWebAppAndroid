package no.usn.gruppe4.crmwebappandroid.models.appointment

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Appointment(
    val _id: String?,
    var comment: String?,
    var customers: MutableList<OneAppointmentResponse.Customer>?,
    var date: Date?,
    var duration: Int?,
    var employees: MutableList<OneAppointmentResponse.Employee>?,
    val paymentReceived: Boolean?,
    val ratings: MutableList<OneAppointmentResponse.Rating>?,
    var services: MutableList<OneAppointmentResponse.Service>?,
    var timeindex: Int?,
) : Parcelable {
    @SuppressLint("SimpleDateFormat")
    fun checkDate(date: Date): Boolean{
        val fmt = SimpleDateFormat("yyyyMMdd")
        return fmt.format(this.date!!).equals(fmt.format(date))
    }

    fun checkDatePast(date: Date): Boolean{
        return this.date!!.before(date)
    }

    @Parcelize
    data class newAppointment(
        var _id: String?,
        var date: Date?,
        var timeindex: Int?,
        var comment: String,
        var duration: Int?,
        var services: MutableList<_service>,
        var customers: MutableList<_customer>,
        var employees: MutableList<_employee>,
    ): Parcelable {
        //adds the service id to the list
        fun addService(service: Service){
            var temp = mutableListOf<_service>()
            for (i in services)
                temp.add(i)

            temp.add(_service(service._id.toString()))
            services = temp
        }

        fun removeService(service: Service){
            var temp = mutableListOf<_service>()
            for (i in services)
                temp.add(i)

            for (i in temp.indices){
                if (temp.get(i).equals(service._id))
                    temp.remove(temp.get(i))
            }
            services = temp
        }

        fun addCustomer(customer: Customer){
            var temp = mutableListOf<_customer>()
            for (i in customers)
                temp.add(i)
            temp.add(_customer(customer._id.toString()))
            customers = temp
        }

        fun removeCustomer(customer: Customer){
            var temp = mutableListOf<_customer>()
            for (i in customers)
                temp.add(i)

            for (i in temp.indices){
                if (temp.get(i).equals(customer._id))
                    temp.remove(temp.get(i))
            }
            customers = temp
        }

        fun addEmployee(employee: Employee){
            var temp = mutableListOf<_employee>()
            for (i in employees)
                temp.add(i)
            temp.add(_employee(employee._id.toString()))
            employees = temp
        }

        fun removeEmployee(employee: Employee){
            var temp = mutableListOf<_employee>()
            for (i in employees)
                temp.remove(i)

            for (i in temp.indices){
                if (temp.get(i).equals(employee._id))
                    temp.remove(temp.get(i))
            }
            employees = temp
        }

        @Parcelize
        data class _service(val _service: String):Parcelable{
        }

        @Parcelize
        data class _employee(val _employee: String):Parcelable

        @Parcelize
        data class _customer(val _customer: String):Parcelable

    }
}
