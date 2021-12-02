package no.usn.gruppe4.crmwebappandroid.models.todo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import no.usn.gruppe4.crmwebappandroid.retrofit.RetrofitInstance
import java.lang.Exception

const val TAG  = "TodoViewModel"

class TodoViewModel: ViewModel() {

    private val _todo: MutableLiveData<List<Todo>> = MutableLiveData()

    val todos : LiveData<List<Todo>>
            get() = _todo

    val _status: MutableLiveData<Int> = MutableLiveData(1)
    val status : LiveData<Int>
        get() = _status

    fun getTodo(){
        viewModelScope.launch {
            try {
                val data = RetrofitInstance.api.getTodos()
                _todo.value = data.data.todos
                Log.i(TAG,"data: $data")
            }catch (e: Exception){
                Log.i(TAG,"Error $e")
            }finally {
                _status.value = 1
            }
        }
    }

    // New todos
    fun newTodo(todo: Todo){
        viewModelScope.launch {
            try {
                RetrofitInstance.api.addTodo(Todo.AddTodo(todo))
                Log.i(TAG,"Todo is added")
            }catch (e: Exception){
                Log.i(TAG, "Error: $e")
            }finally {
                _status.value = 2
            }
        }
    }

    // Delete todos
    fun deleteTodo(idRequest: Todo.DeleteTodo){
        viewModelScope.launch {
            try {
                RetrofitInstance.api.deleteTodo(idRequest)
                Log.i(TAG,"Deleted completed todos of items $idRequest")
            }catch (e: Exception){
                Log.i(TAG,"Error $e")
            }finally {
                _status.value = 2
            }
        }
    }

    // Sett todos completed

    fun completTodo(todoComplete: Todo.SetComplete){
        viewModelScope.launch {
            try {
                RetrofitInstance.api.setTodoComplete(todoComplete)
            }catch (e: Exception) {
                Log.i(TAG, "Error $e")
            }finally {
                _status.value = 2
            }
        }
    }



}