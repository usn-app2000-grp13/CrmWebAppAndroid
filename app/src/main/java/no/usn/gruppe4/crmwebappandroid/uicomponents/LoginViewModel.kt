package no.usn.gruppe4.crmwebappandroid.uicomponents

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import no.usn.gruppe4.crmwebappandroid.models.login.LoginRequest
import no.usn.gruppe4.crmwebappandroid.models.login.SessionResponse
import no.usn.gruppe4.crmwebappandroid.retrofit.RetrofitInstance


private const val TAG = "LoginViewModel"

class LoginViewModel : ViewModel() {

    private val _logged: MutableLiveData<Boolean> = MutableLiveData()
    val logged: LiveData<Boolean>
        get() = _logged

    private val _session: MutableLiveData<SessionResponse.Data> = MutableLiveData()
    val session: LiveData<SessionResponse.Data>
        get() = _session


    fun loginCall(req: LoginRequest){
        viewModelScope.launch {
            try {
                //val request = LoginRequest("test", "test@test.tes")
                val data = RetrofitInstance.api.loginUser(req)
                _session.value = data.data
                Log.i(TAG, "login data: $data")
                _logged.value = true
            }catch (e: Exception){

            }
        }
    }

    init {
        _logged.value = false
    }
}