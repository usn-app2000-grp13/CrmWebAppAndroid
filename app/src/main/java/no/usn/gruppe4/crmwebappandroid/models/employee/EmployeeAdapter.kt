package no.usn.gruppe4.crmwebappandroid.models.employee

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import no.usn.gruppe4.crmwebappandroid.R


class EmployeeAdapter(private val context: Context, private val dataset: List<Employee>) :
    RecyclerView.Adapter<EmployeeAdapter.ItemViewHolder>() {
    private lateinit var mlistener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mlistener = listener

    }

    class ItemViewHolder(val view: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(view) {
        val textViewFirstname: TextView = view.findViewById(R.id.calItemFirstname)
        val textViewLastname: TextView = view.findViewById(R.id.calItemLastname)
        val textViewPhone: TextView = view.findViewById(R.id.calItemPhone)
        val textViewEmail: TextView = view.findViewById(R.id.calItemEmail)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.employee_item, parent, false)
        return ItemViewHolder(adapterLayout, mlistener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textViewFirstname.text = item.firstname
        holder.textViewLastname.text = item.lastname
        holder.textViewPhone.text = item.phone

        holder.textViewEmail.text = item.email
    }


    override fun getItemCount(): Int {
        return dataset.size
    }
}