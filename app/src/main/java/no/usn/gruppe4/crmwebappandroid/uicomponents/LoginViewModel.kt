package no.usn.gruppe4.crmwebappandroid.uicomponents

import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import no.usn.gruppe4.crmwebappandroid.models.login.*
import no.usn.gruppe4.crmwebappandroid.retrofit.RetrofitInstance
import java.lang.IllegalArgumentException


private const val TAG = "LoginViewModel"

class LoginViewModel() : ViewModel() {


    private val _session: MutableLiveData<SessionResponse.Data> = MutableLiveData()
    val session: LiveData<SessionResponse.Data>
        get() = _session

    private val _status: MutableLiveData<Int> = MutableLiveData(1)
    val status: LiveData<Int>
        get() = _status


    fun loginCall(req: LoginRequest): Boolean{
        var status = false
        viewModelScope.launch {
            try {
                _status.value = 2
                //val request = LoginRequest("test", "test@test.tes")

                val data = RetrofitInstance.api.loginUser(req)
                _session.value = data.data
                Log.i(TAG, "login data: $data")
                status = true
                _status.value = 3

            }catch (e: Exception){
                Log.i(TAG, "login Error: $e")
                _status.value = 1
            }

        }
        return status
    }

    fun resetPassword(req: ResetPasswordRequest){
        viewModelScope.launch {
            try {
                _status.value = 2
                RetrofitInstance.api.resetPassword(req)
                _status.value = 1
            }catch (e: Exception){
                Log.i(TAG, "Password Reset Error: $e")
                _status.value = 1
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