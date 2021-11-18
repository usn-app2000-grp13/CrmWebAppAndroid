package no.usn.gruppe4.crmwebappandroid.models.customer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import no.usn.gruppe4.crmwebappandroid.retrofit.RetrofitInstance
import androidx.lifecycle.viewModelScope
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import kotlinx.coroutines.launch

const val TAG  = "CustomerViewModel"

class CustomerViewModel: ViewModel()  {

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

    fun newCustomer(customer: Customer) {
        //print(customer);
        viewModelScope.launch {
            try {
                RetrofitInstance.api.postCustomer(customer)
                //here should there be a boolean
                Log.i(TAG, "Customer created")
            }catch (e: Exception){
                Log.i(TAG, "Error: $e")
            }
        }
    }

    fun updateCustomer(customer: Customer) {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.putCustomer(customer)
                //here should there be a boolean
                Log.i(no.usn.gruppe4.crmwebappandroid.models.customer.TAG, "Customer updated")
            }catch (e: Exception){
                Log.i(no.usn.gruppe4.crmwebappandroid.models.customer.TAG, "Error: $e")
            }
        }
    }

    fun deleteCustomer(deleteCustomer: DeleteCustomer) {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.deleteCustomer(deleteCustomer)
                Log.i(no.usn.gruppe4.crmwebappandroid.models.customer.TAG, "Customer deleted")
            }catch (e: Exception){
                Log.i(no.usn.gruppe4.crmwebappandroid.models.customer.TAG, "Error: $e")
            }
        }
    }
    data class DeleteCustomer(val id: String?)
}