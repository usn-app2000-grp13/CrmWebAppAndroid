package no.usn.gruppe4.crmwebappandroid.uicomponents

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import no.usn.gruppe4.crmwebappandroid.models.IdRequest
import no.usn.gruppe4.crmwebappandroid.models.appointment.Appointment
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee
import no.usn.gruppe4.crmwebappandroid.models.mail.MailRequest
import no.usn.gruppe4.crmwebappandroid.models.mail.RatingRequest
import no.usn.gruppe4.crmwebappandroid.models.stats.employeePop
import no.usn.gruppe4.crmwebappandroid.models.stats.servicePop
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

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _nrTodo: MutableLiveData<Int> = MutableLiveData()
    val nrTodo: LiveData<Int>
        get() = _nrTodo

    private val _statEmployee: MutableLiveData<List<employeePop>> = MutableLiveData()
    val statEmployee: LiveData<List<employeePop>>
        get() = _statEmployee

    private val _statService: MutableLiveData<List<servicePop>> = MutableLiveData()
    val statService: LiveData<List<servicePop>>
        get() = _statService

    fun setCurAppointment(app: Appointment.newAppointment){
        _curAppointment.value = app
    }
    fun getMyAppointmentsDate(_id: String, milles: Long){
        _errorMessage.value = null
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val data = RetrofitInstance.api.getMyAppointments2(_id, correctDate(milles))
                _appointments.value = data.data
                Log.i(TAG, "appointment data: $data")
            }catch (e: Exception){
                _errorMessage.value = e.message
                Log.i(TAG, "Error: $e")
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun newAppointment(appointment: Appointment.newAppointment){
        _errorMessage.value = null
        _isLoading.value = true
        viewModelScope.launch {
            try{
                RetrofitInstance.api.addAppointment(appointment)
                Log.i(TAG, "Appointment added")
            }catch (e : Exception){
                _errorMessage.value = e.message
                Log.i(TAG, "Error: $e")
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun removeAppointment(idRequest: IdRequest){
        _errorMessage.value = null
        _isLoading.value = true
        viewModelScope.launch {
            try{
                _status.value = false
                RetrofitInstance.api.deleteAppointment(idRequest)
                Log.i(TAG, "Appointment removed")
                _status.value = true
            }catch (e : Exception){
                _errorMessage.value = e.message
                Log.i(TAG, "Error: $e")
            }finally {
                _isLoading.value = true
            }
        }
    }

    fun updateAppointment(appointment: Appointment.newAppointment){
        _errorMessage.value = null
        _isLoading.value = true
        viewModelScope.launch {
            try{
                RetrofitInstance.api.updateAppointment(appointment)
                Log.i(TAG, "Appointment removed")
            }catch (e : Exception){
                _errorMessage.value = e.message
                Log.i(TAG, "Error: $e")
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun getCustomers(){
        _errorMessage.value = null
        _isLoading.value = true
        viewModelScope.launch {
            try{
                val data = RetrofitInstance.api.getCustomers()
                _customers.value = data.data
                Log.i(TAG, "Customer data: $data")
            }catch (e: Exception){
                _errorMessage.value = null
                Log.i(TAG, "Error: $e")
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun getEmployees(){
        _errorMessage.value = null
        _isLoading.value = true
        viewModelScope.launch {
            try{
                val data = RetrofitInstance.api.getEmployees()
                _employees.value = data.data
                Log.i(TAG, "Employee data: $data")
            }catch (e: Exception){
                _errorMessage.value = e.message
                Log.i(TAG, "Error: $e")
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun getServices(){
        _errorMessage.value = null
        _isLoading.value = true
        viewModelScope.launch {
            try{
                val data = RetrofitInstance.api.getServices()
                _services.value = data.data
                Log.i(TAG, "Service data: $services")
            }catch (e: Exception){
                _errorMessage.value = e.message
                Log.i(TAG, "Error: $e")
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun sendUserMail(req: MailRequest){
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                RetrofitInstance.api.sendMail(req)
            }catch (e: Exception){
                _errorMessage.value = e.message
                Log.i(TAG, "Error: $e")
            }
        }
    }

    fun sendRatingRequest(req: RatingRequest){
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                RetrofitInstance.api.sendRatingRequest(req)
            }catch (e: Exception){
                _errorMessage.value = e.message
                Log.i(TAG, "Error: $e")
            }
        }
    }

    fun getServicePop(){
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val data = RetrofitInstance.api.getServiceStats()
                _statService.value = data.data
                Log.i(TAG, "data service pop: $data")
            }catch (e: Exception){
                _errorMessage.value = e.message
                Log.i(TAG, "Error: $e")
            }
        }
    }

    fun getEmployeePop(){
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val data = RetrofitInstance.api.getEmployeeStats()
                _statEmployee.value = data.data
                Log.i(TAG, "data employee pop: $data")
            }catch (e: Exception){
                _errorMessage.value = e.message
                Log.i(TAG, "Error: $e")
            }
        }
    }

    fun getTodoCount(){
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val data = RetrofitInstance.api.getTodos()
                _nrTodo.value = data.data.todos.size
            }catch (e: Exception){
                _errorMessage.value = e.message
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

