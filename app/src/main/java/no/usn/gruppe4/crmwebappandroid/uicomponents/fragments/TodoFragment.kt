package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
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

        todoAdepter = TodoAdapter(requireContext(),todolist)
        binding.TodoRv.adapter = todoAdepter

        todoAdepter.setOnItemClickListener(object : TodoAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {

            }

            override fun onDeleteClick(position: Int) {
                todolist.removeAt(position)
                todoAdepter.notifyDataSetChanged()
                val id = todolist.get(position)._id
                Log.i("check", "check if clicked delete $position $id")
                viewModel.deleteTodo(Todo.deleteTodo(sharedPreferences.get("id"), IdRequest(id)))
            }

            override fun onCheckClicked(position: Int) {
                TODO("Not yet implemented")
            }

        })
/*
        binding.todoAddBT.setOnClickListener {
            val todoTitle = todoET.text.toString()
            val todo = Todo(todoTitle)
            if (todoTitle.isNotEmpty()) {
                todoAdepter.addTodo(todo)
                todoET.text.clear()
            }
            viewModel.newTodo(todo)
        }

 */
        // Inflate the layout for this fragment
        return binding.root
    }

}