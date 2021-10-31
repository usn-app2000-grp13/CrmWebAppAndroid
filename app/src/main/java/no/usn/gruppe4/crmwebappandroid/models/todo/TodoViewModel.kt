package no.usn.gruppe4.crmwebappandroid.models.todo

import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import no.usn.gruppe4.crmwebappandroid.models.IdRequest
import no.usn.gruppe4.crmwebappandroid.retrofit.RetrofitInstance
import java.lang.Exception

const val TAG  = "TodoViewModel"

class TodoViewModel: ViewModel() {

    private val _todo: MutableLiveData<List<Todo>> = MutableLiveData()

    val todos : LiveData<List<Todo>>
            get() = _todo

    fun getTodo(){
        viewModelScope.launch {
            try {
                val data = RetrofitInstance.api.getTodos()
                _todo.value = data.data.todos
            }catch (e: Exception){
                Log.i(TAG,"Error $e")
            }
        }
    }

    fun newTodo(todos: Todo){
        viewModelScope.launch {
            try {
                RetrofitInstance.api.addTodo(todos)
                Log.i(TAG,"Todo er lagt til")
            }catch (e: Exception){
                Log.i(TAG, "Error: $e")
            }
        }
    }

    fun deleteTodo(todos: IdRequest){
        viewModelScope.launch {
            try {
                RetrofitInstance.api.deleteTodo(todos)
                Log.i(TAG,"Todo deleted")
            }catch (e: Exception){
                Log.i(TAG,"Error $e")
            }
        }

    }

}