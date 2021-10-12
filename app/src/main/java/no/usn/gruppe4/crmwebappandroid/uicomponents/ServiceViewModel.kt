package no.usn.gruppe4.crmwebappandroid.uicomponents

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import no.usn.gruppe4.crmwebappandroid.retrofit.RetrofitInstance

private const val TAG = "ServiceViewModel"

class ServiceViewModel: ViewModel() {

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
}