package no.usn.gruppe4.crmwebappandroid.models.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import no.usn.gruppe4.crmwebappandroid.R

class TodoAdapter(

    private val context : Context,
    private val todos: MutableList<Todo>

) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private lateinit var mlistener : OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
        fun onDeleteClick(position: Int)
        fun onCheckClicked(position: Int)
    }

    class TodoViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val todoTw : TextView = itemView.findViewById(R.id.todoTw)
        val todoCB : CheckBox = itemView.findViewById(R.id.todoCB)
        val btnDelete: ImageView = itemView.findViewById(R.id.todoDeleteBT)
        val checkBox: CheckBox = itemView.findViewById(R.id.todoCB)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
            btnDelete.setOnClickListener {
                listener.onDeleteClick(adapterPosition)
            }
            checkBox.setOnClickListener {
                val value = checkBox.isChecked
                if (value == true){
                    listener.onCheckClicked(adapterPosition)
                    checkBox.isChecked = true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val adapterLayout =  LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(adapterLayout, mlistener)

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mlistener = listener
    }

    fun addTodo(todo: Todo){
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]
        holder.todoTw.text = curTodo.description
        holder.todoCB.isChecked = curTodo.completed
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}