package no.usn.gruppe4.crmwebappandroid.models.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import no.usn.gruppe4.crmwebappandroid.models.IdRequest
import no.usn.gruppe4.crmwebappandroid.models.employee.EmployeeViewModel
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import no.usn.gruppe4.crmwebappandroid.retrofit.RetrofitInstance

private const val TAG = "ServiceViewModel"

class ServiceViewModel: ViewModel() {

    private val _adding: MutableLiveData<Boolean> = MutableLiveData()
    val adding: LiveData<Boolean>
        get() = _adding

    // _services er ikke open til andre klasser
    private val _services: MutableLiveData<List<Service>> = MutableLiveData()
    // en kopi som er open til andre klasser
    val services: LiveData<List<Service>>
        get() = _services

    /**
     * getServices henter services fra database og legger dem i _services liste
     */
    fun getServices(){
        viewModelScope.launch {
            // gjør database call og setter over json til objekter og legger disse i en variabel
            val data = RetrofitInstance.api.getServices()
            // legger listen i _services (services)
            _services.value = data.data
            // printer ut data
            Log.i(TAG, "Services: $data")
        }
    }

    /**
     * add a new service
     */
    fun newService(item: Service){
        viewModelScope.launch {
            RetrofitInstance.api.newService(item)
            _adding.value = true;
        }
    }

    fun removeService(idRequest: IdRequest){
        viewModelScope.launch {
            try{
                RetrofitInstance.api.deleteService(idRequest)
                Log.i(TAG, "Delete complete of item: ${idRequest._id}")
            }catch (e: Exception){
                Log.i(TAG, "Error: $e")
            }
        }
    }

    fun updateService(service: Service){
        viewModelScope.launch {
            try{
                RetrofitInstance.api.updateService(service)
                _adding.value = true
                Log.i(TAG, "added new service: $service")
            }catch (e: Exception){
                Log.i(TAG, "Error: $e")
            }
        }
    }

    fun newAction(){
        _adding.value = false;
    }

    //test
    data class DeleteService(val id: String?)
}