package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.android.synthetic.main.item_todo.*
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentTodoBinding
import no.usn.gruppe4.crmwebappandroid.models.todo.Todo
import no.usn.gruppe4.crmwebappandroid.models.todo.TodoAdapter
import no.usn.gruppe4.crmwebappandroid.models.todo.TodoViewModel

class TodoFragment : Fragment() {

    private lateinit var  binding : FragmentTodoBinding
    private lateinit var todoAdepter: TodoAdapter
    private var todolist =  mutableListOf<Todo>()
    private lateinit var viewModel: TodoViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTodoBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        viewModel.getTodo()


        viewModel.todos.observe(viewLifecycleOwner, { todo ->
            todolist.clear()
            todolist.addAll(todo)
            todoAdepter.notifyDataSetChanged()
        })

        todoAdepter = TodoAdapter(requireContext(),todolist)
        binding.TodoRv.adapter = todoAdepter
        /*  var todos: MutableList<Todo> = mutableListOf(Todo("Klippe håret"),
               Todo("Trene"),
               Todo("Vaske doen"))                                                           //Skal ta i mot en liste av todo objekter fra databasen
         */

        todoAdepter.setOnItemClickListener(object : TodoAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {

            }

        })

        binding.todoAddBT.setOnClickListener {
            val todoTitle = todoET.text.toString()
            if (todoTitle.isNotEmpty()) {
                val todo = Todo(todoTitle)
                todoAdepter.addTodo(todo)
                todoET.text.clear()
            }
        }

        todoDeleteBT.setOnClickListener {
            todoAdepter.deletedDoneTodos()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}