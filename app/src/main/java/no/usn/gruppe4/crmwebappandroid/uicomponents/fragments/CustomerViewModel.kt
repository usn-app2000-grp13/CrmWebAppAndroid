package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.retrofit.RetrofitInstance

private const val TAG = "CustomerViewModel"

//en av grunnene vi trenger en viewmodel er for å beholde data på skjermrotasjon osv.
class CustomerViewModel: ViewModel() {

    //en private mutable live data liste for å holde customer objekter
    private val _customers: MutableLiveData<List<Customer>> = MutableLiveData()
    //en akseserbar liste med customer objekter som er en kopi av _customer (men kan endres)
    val customers: LiveData<List<Customer>>
        get() = _customers


    // async metode som gjør et trygg API call
    fun getCustomers(){
        viewModelScope.launch {
            try{
                val data = RetrofitInstance.api.getCustomers()
                //tar en List<Customer> og legger den i _customers
                _customers.value = data.data
                Log.i(TAG, "Customer data: $data")
            }catch (e: Exception){
                Log.i(TAG, "Error: $e")
            }
        }
    }


}