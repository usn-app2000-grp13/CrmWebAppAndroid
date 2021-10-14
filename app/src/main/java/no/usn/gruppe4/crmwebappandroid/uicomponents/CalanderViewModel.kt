package no.usn.gruppe4.crmwebappandroid.uicomponents

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import no.usn.gruppe4.crmwebappandroid.models.IdRequest
import no.usn.gruppe4.crmwebappandroid.models.appointment.Appointment
import no.usn.gruppe4.crmwebappandroid.models.appointment.AppointmentResponse
import no.usn.gruppe4.crmwebappandroid.retrofit.RetrofitInstance
import java.util.*

private const val TAG = "CalanderViewModel"

class CalanderViewModel: ViewModel() {

    private val _appointments: MutableLiveData<List<Appointment>> = MutableLiveData()
    val appointment: LiveData<List<Appointment>>
        get() = _appointments


    fun getAppointments(req: String){
        viewModelScope.launch {
            try {
                //val data = RetrofitInstance.api.getAppointment(req)
                val data = RetrofitInstance.api.getAppointment("602a7f4891d34d18402f4e44")
                Log.i(TAG, "appointment data: $data")
            }catch (e: Exception){
                Log.i(TAG, "Error: $e")
            }

        }
    }

    fun getAppointments(){
        viewModelScope.launch {
            try {
                val data = RetrofitInstance.api.getAppointments()
                //val data = RetrofitInstance.api.getAppointments("602a7f4891d34d18402f4e44")
                //_appointments.value = data.data
                Log.i(TAG, "appointment data: $data")
            }catch (e: Exception){
                Log.i(TAG, "Error: $e")
            }
        }
    }

    fun getMyAppointments(_id: String){
        viewModelScope.launch {
            try {
                val data = RetrofitInstance.api.getMyAppointments(_id)
                //_appointments.value = data.data
                Log.i(TAG, "appointment data: $data")
            }catch (e: Exception){
                Log.i(TAG, "Error: $e")
            }
        }
    }

    fun getMyAppointmentsDate(_id: String, date: Date){
        viewModelScope.launch {
            try {
                val data = RetrofitInstance.api.getMyAppointments2(_id, date)
                _appointments.value = data.data
                //_appointments.value = data.data
                Log.i(TAG, "appointment data: $data")
            }catch (e: Exception){
                Log.i(TAG, "Error: $e")
            }
        }
    }
}

