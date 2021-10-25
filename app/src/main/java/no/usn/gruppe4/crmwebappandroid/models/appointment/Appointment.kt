package no.usn.gruppe4.crmwebappandroid.models.appointment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import java.util.*

@Parcelize
data class Appointment(
    val _id: String?,
    val comment: String?,
    val customers: List<OneAppointmentResponse.Customer>?,
    val date: Date?,
    val duration: Int?,
    val employees: List<OneAppointmentResponse.Employee>?,
    val paymentReceived: Boolean?,
    val ratings: List<AppointmentResponse.Rating>?,
    var services: List<OneAppointmentResponse.Service>?,
    val timeindex: Int?,
) : Parcelable {
    fun checkDate(date: Date): Boolean{
        return date.year == this.date!!.year && date.month == this.date!!.month && date.day == this.date!!.day
    }


    @Parcelize
    data class newAppointment(var date: Date?,
                              var timeindex: Int?,
                              var comment: String,
                              var duration: Int?,
                              var services: List<_service>,
                              var customers: List<_customer>,
                              var employees: List<_employee>,
    ): Parcelable {
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

            for (i in temp){
                if (i._service.equals(service._id))
                    temp.remove(i)
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

            for (i in temp){
                if (i._customer.equals(customer._id))
                    temp.remove(i)
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

            for (i in temp){
                if (i._employee.equals(employee._id))
                    temp.remove(i)
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
