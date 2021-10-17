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
class CustomerViewModel: ViewModel() {

    private val _customers: MutableLiveData<List<Customer>> = MutableLiveData()
    val customers: LiveData<List<Customer>>
        get() = _customers


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
}