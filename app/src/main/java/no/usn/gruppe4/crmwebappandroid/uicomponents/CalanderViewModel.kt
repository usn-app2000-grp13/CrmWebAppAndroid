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

    private val _date: MutableLiveData<Date> = MutableLiveData()
    val date: LiveData<Date>
        get() = _date


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

