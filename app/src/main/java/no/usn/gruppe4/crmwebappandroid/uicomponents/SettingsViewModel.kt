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


    fun getMyData(_id: String){
        viewModelScope.launch {
            try {
                val data = RetrofitInstance.api.getOneEmployee(_id)
                _user.value = data.data
            }catch (e: Exception){
                Log.i(TAG, e.toString())
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