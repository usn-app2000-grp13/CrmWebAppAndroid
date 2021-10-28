package no.usn.gruppe4.crmwebappandroid.uicomponents

import android.app.Service
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import no.usn.gruppe4.crmwebappandroid.models.IdRequest
import no.usn.gruppe4.crmwebappandroid.models.appointment.Appointment
import no.usn.gruppe4.crmwebappandroid.models.appointment.AppointmentResponse
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee
import no.usn.gruppe4.crmwebappandroid.retrofit.RetrofitInstance
import java.util.*

private const val TAG = "CalanderViewModel"

class CalanderViewModel: ViewModel() {

    private val _appointments: MutableLiveData<List<Appointment>> = MutableLiveData()
    val appointment: LiveData<List<Appointment>>
        get() = _appointments

    private val _customers: MutableLiveData<List<Customer>> = MutableLiveData()
    val customers: LiveData<List<Customer>>
        get() = _customers

    private val _employees: MutableLiveData<List<Employee>> = MutableLiveData()
    val employees: LiveData<List<Employee>>
        get() = _employees

    private val _services: MutableLiveData<List<no.usn.gruppe4.crmwebappandroid.models.service.Service>> = MutableLiveData()
    val services: LiveData<List<no.usn.gruppe4.crmwebappandroid.models.service.Service>>
        get() = _services

    private val _date: MutableLiveData<Date> = MutableLiveData()
    val date: LiveData<Date>
        get() = _date

    private val _curAppointment: MutableLiveData<Appointment.newAppointment> = MutableLiveData()
    val curAppointment: LiveData<Appointment.newAppointment>
        get() = _curAppointment

    private val _status: MutableLiveData<Boolean> = MutableLiveData(true)
    val status: LiveData<Boolean>
        get() = _status


    fun setCurAppointment(app: Appointment.newAppointment){
        _curAppointment.value = app
    }
    fun getMyAppointmentsDate(_id: String, milles: Long){
        viewModelScope.launch {
            try {
                val data = RetrofitInstance.api.getMyAppointments2(_id, correctDate(milles))
                _appointments.value = data.data
                Log.i(TAG, "appointment data: $data")
            }catch (e: Exception){
                Log.i(TAG, "Error: $e")
            }
        }
    }

    fun newAppointment(appointment: Appointment.newAppointment){
        viewModelScope.launch {
            try{
                RetrofitInstance.api.addAppointment(appointment)
                Log.i(TAG, "Appointment added")
            }catch (e : Exception){
                Log.i(TAG, "Error: $e")
            }
        }
    }

    fun removeAppointment(idRequest: IdRequest){
        viewModelScope.launch {
            try{
                _status.value = false
                RetrofitInstance.api.deleteAppointment(idRequest)
                Log.i(TAG, "Appointment removed")
                _status.value = true
            }catch (e : Exception){
                Log.i(TAG, "Error: $e")
            }
        }
    }

    fun updateAppointment(appointment: Appointment){
        viewModelScope.launch {
            try{
                RetrofitInstance.api.updateAppointment(appointment)
                Log.i(TAG, "Appointment removed")
            }catch (e : Exception){
                Log.i(TAG, "Error: $e")
            }
        }
    }

    fun getAllAppointments(){
        viewModelScope.launch {
            try {
                val data = RetrofitInstance.api.getAppointments()
                _appointments.value = data.data
                Log.i(TAG, "appointment data: $data")
            }catch (e: Exception){
                Log.i(TAG, "Error: $e")
            }
        }
    }

    fun getCustomers(){
        viewModelScope.launch {
            try{
                val data = RetrofitInstance.api.getCustomers()
                _customers.value = data.data
                Log.i(TAG, "Customer data: $data")
            }catch (e: Exception){
                Log.i(TAG, "Error: $e")
            }
        }
    }

    fun getEmployees(){
        viewModelScope.launch {
            try{
                val data = RetrofitInstance.api.getEmployees()
                _employees.value = data.data
                Log.i(TAG, "Employee data: $data")
            }catch (e: Exception){
                Log.i(TAG, "Error: $e")
            }
        }
    }

    fun getServices(){
        viewModelScope.launch {
            try{
                val data = RetrofitInstance.api.getServices()
                _services.value = data.data
                Log.i(TAG, "Service data: $services")
            }catch (e: Exception){
                Log.i(TAG, "Error: $e")
            }
        }
    }

    fun getSchemaData(){
        getEmployees()
        getCustomers()
        getServices()
    }

    fun changeDate(newDate: Date){
        _date.value = newDate
        Log.i("Date", _date.value.toString())
    }

    init {
        _date.value = Date()
    }

    private fun correctDate(milles: Long): Date{
        val newDate = Date(milles-24*60*60*1000)
        return newDate
    }
}

