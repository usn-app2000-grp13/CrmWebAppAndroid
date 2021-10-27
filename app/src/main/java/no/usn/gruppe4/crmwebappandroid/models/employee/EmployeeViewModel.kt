package no.usn.gruppe4.crmwebappandroid.models.employee

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import no.usn.gruppe4.crmwebappandroid.retrofit.RetrofitInstance

const val TAG  = "EmployeeViewModel"


class EmployeeViewModel: ViewModel() {

    //hold the employee objects
    private val _employees: MutableLiveData<List<Employee>> = MutableLiveData()

    //create a list of employees
    val employee : LiveData<List<Employee>>
        get() = _employees

    //async method for api call
    fun getEmployees(){
        viewModelScope.launch {
            try {
                //call the previously created retrofit get call
                val data = RetrofitInstance.api.getEmployees()
                //Take a list of Employees and put in _employees
                _employees.value = data.data

            }catch(e: Exception){
                Log.i(TAG,"Error: $e")}
        }
    }
    //async method for crating a new employee
    fun newEmployee(employee: Employee){
        viewModelScope.launch {
            try {

                RetrofitInstance.api.postEmployees(employee)
                //here should there be a boolean
                Log.i(TAG, "Employee added")
            }catch (e: Exception){
                Log.i(TAG, "Error: $e")
            }
        }
    }
    //async delete employee
    fun deleteEmployee(employee: DeleteEmployee){
        viewModelScope.launch {
        try {
            RetrofitInstance.api.deleteEmployees(employee)
            //here should there be a boolean
            Log.i(TAG, "Employee deleted")
        }catch (e: Exception){
            Log.i(TAG, "Error: $e")
        }
        }
    }

    fun alterEmployee(employee: Employee){
        viewModelScope.launch  {
            try {
                RetrofitInstance.api.putEmployees(employee)
                Log.i(TAG, "Employee altered")
            } catch (e: Exception) {
                Log.i(TAG, "Error: $e")
            }
        }
    }
    data class DeleteEmployee(val id: String?)
}