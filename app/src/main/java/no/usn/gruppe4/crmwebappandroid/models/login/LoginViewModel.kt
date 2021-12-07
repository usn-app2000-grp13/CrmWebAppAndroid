package no.usn.gruppe4.crmwebappandroid.models.login

import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import no.usn.gruppe4.crmwebappandroid.models.login.*
import no.usn.gruppe4.crmwebappandroid.retrofit.RetrofitInstance
import no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.NewCompanyFragment
import java.lang.IllegalArgumentException


private const val TAG = "LoginViewModel"

class LoginViewModel() : ViewModel() {


    private val _session: MutableLiveData<SessionResponse.Data> = MutableLiveData()
    val session: LiveData<SessionResponse.Data>
        get() = _session

    private val _status: MutableLiveData<Int> = MutableLiveData(1)
    val status: LiveData<Int>
        get() = _status

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    fun loginCall(req: LoginRequest): Boolean{
        _errorMessage.value = null
        _isLoading.value = true
        var status = false
        viewModelScope.launch {
            try {
                _status.value = 2
                val data = RetrofitInstance.api.loginUser(req)
                _session.value = data.data
                Log.i(TAG, "login data: $data")
                status = true
                _status.value = 3

            }catch (e: Exception){
                _errorMessage.value = e.message
                Log.i(TAG, "login Error: $e")
                _status.value = 1
            }finally {
                _isLoading.value = false
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
    fun getPasswordResetVerification(email: String, code: String){
        viewModelScope.launch {
            try {
                RetrofitInstance.api.getPasswordResetVerification(email, code)
                Log.i(TAG, "password reset continues")
            }catch (e: Exception){
                Log.i(TAG, e.toString())
            }
        }
    }
    fun setPassword(newPassword: NewPassword){
        viewModelScope.launch {
            try {
                RetrofitInstance.api.setPassword(newPassword)
                Log.i(TAG, "password reset finished")
            }catch (e: Exception){
                Log.i(TAG, e.toString())
            }
        }
    }

    fun newCompany(company: NewCompanyFragment.newCompany){
        _errorMessage.value = null
        _isLoading.value = true
        _status.value = 1   //1 = in progress
        viewModelScope.launch {
            try {
                RetrofitInstance.api.newCompany(company)
                _status.value = 6   //2 = complete
            }catch (e: Exception){
                _errorMessage.value = e.message
                _status.value = 3   //3 = error
                Log.i("LoginViewModel", "error: $e")
            }finally {
                _isLoading.value = false
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
    data class PasswordResetSuccess(val success: Boolean?)
    data class NewPassword(val newPassword: String?)
}