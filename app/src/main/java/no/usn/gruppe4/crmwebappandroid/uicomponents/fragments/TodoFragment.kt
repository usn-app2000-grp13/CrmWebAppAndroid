package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.android.synthetic.main.item_todo.*
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentTodoBinding
import no.usn.gruppe4.crmwebappandroid.models.IdRequest
import no.usn.gruppe4.crmwebappandroid.models.login.SecSharePref
import no.usn.gruppe4.crmwebappandroid.models.login.SharedPrefInterface
import no.usn.gruppe4.crmwebappandroid.models.todo.Todo
import no.usn.gruppe4.crmwebappandroid.models.todo.TodoAdapter
import no.usn.gruppe4.crmwebappandroid.models.todo.TodoViewModel

class TodoFragment : Fragment() {

    private lateinit var  binding : FragmentTodoBinding
    private lateinit var todo: Todo
    private lateinit var todoAdepter: TodoAdapter
    private var todolist =  mutableListOf<Todo>()
    private lateinit var viewModel: TodoViewModel
    private lateinit var sharedPreferences: SharedPrefInterface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTodoBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        viewModel.getTodo()

        sharedPreferences = SecSharePref(requireContext(), "secrets")
        val id = sharedPreferences.get("id")


        viewModel.todos.observe(viewLifecycleOwner, { todo ->
            todolist.clear()
            todolist.addAll(todo)
            todoAdepter.notifyDataSetChanged()
            binding.TodoRv.scheduleLayoutAnimation()
        })

        viewModel.status.observe(viewLifecycleOwner, {
            if (it == 2){
                viewModel.getTodo()
            }
        })

        todoAdepter = TodoAdapter(requireContext(),todolist)
        binding.TodoRv.adapter = todoAdepter

        todoAdepter.setOnItemClickListener(object : TodoAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {

            }

            override fun onDeleteClick(position: Int) {
                val curId = todolist.get(position)._id
                Log.i("check", "check if clicked delete $position $id")
                viewModel.deleteTodo(Todo.DeleteTodo(id, IdRequest(curId)))
            }

            override fun onCheckClicked(position: Int) {
                    val curId = todolist.get(position)._id
                    Log.i("check", "check if clicked checked $position $id")
                    viewModel.completTodo(Todo.SetComplete(id,Todo.IdTodo(curId!!)))
                    viewModel.getTodo()
            }

        })

        binding.todoAddBT.setOnClickListener {
            val todoTitle = todoET.text.toString()
            val todo = Todo(_id = null, todoTitle)
            if (todoTitle.isNotEmpty()) {
                todoAdepter.addTodo(todo)
                todoET.text.clear()
                viewModel.newTodo(todo)
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}