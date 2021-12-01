package no.usn.gruppe4.crmwebappandroid.uicomponents

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import no.usn.gruppe4.crmwebappandroid.models.appointment.Appointment
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee
import no.usn.gruppe4.crmwebappandroid.retrofit.RetrofitInstance

private const val TAG = "SettingsViewModel"

class SettingsViewModel: ViewModel() {

    private val _user: MutableLiveData<Employee> = MutableLiveData()
    val user: LiveData<Employee>
        get() = _user

    private val _status: MutableLiveData<Int> = MutableLiveData()
    val status: LiveData<Int>
        get() = _status

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    fun getMyData(_id: String){
        _status.value = 2
        viewModelScope.launch {
            try {
                val data = RetrofitInstance.api.getOneEmployee(_id)
                _user.value = data.data
            }catch (e: Exception){
                Log.i(TAG, e.toString())
            }finally {
                _status.value = 1
            }
        }
    }

    fun alterEmployee(employee: Employee){
        _errorMessage.value = null
        _status.value = 2
        viewModelScope.launch  {
            try {
                RetrofitInstance.api.putEmployees(employee)
                Log.i(TAG, "Employee altered")
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.i(TAG, "Error: $e")
            }finally {
                _status.value = 3
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            try{
                RetrofitInstance.api.logoutUser()
            }catch (e: Exception){
                Log.i(TAG, e.toString())
            }
        }
    }
}