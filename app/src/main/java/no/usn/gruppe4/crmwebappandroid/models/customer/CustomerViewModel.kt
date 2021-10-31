package no.usn.gruppe4.crmwebappandroid.models.customer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import no.usn.gruppe4.crmwebappandroid.retrofit.RetrofitInstance
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

const val TAG  = "CustomerViewModel"

class CustomerViewModel: ViewModel()  {

    private val _customers: MutableLiveData<List<Customer>> = MutableLiveData()

    //create a list of customers
    val customer : LiveData<List<Customer>>
        get() = _customers

    //async method for api call
    suspend fun getCustomers(){
        viewModelScope.launch {
            try {
                //call the previously created retrofit get call
                val data = RetrofitInstance.api.getCustomers()
                //Take a list of Employees and put in _employees
                _customers.value = data.data

            }catch(e: Exception){
                Log.i(no.usn.gruppe4.crmwebappandroid.models.customer.TAG,"Error: $e")}
        }
    }

    fun newCustomer(customer: Customer) {
        print(customer);
        viewModelScope.launch {
            try {

                RetrofitInstance.api.postCustomer(customer)
                //here should there be a boolean
                Log.i(no.usn.gruppe4.crmwebappandroid.models.customer.TAG, "Customer created")
            }catch (e: Exception){
                Log.i(no.usn.gruppe4.crmwebappandroid.models.customer.TAG, "Error: $e")
            }
        }

    }
}