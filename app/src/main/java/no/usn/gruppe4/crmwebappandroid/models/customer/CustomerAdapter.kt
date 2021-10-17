package no.usn.gruppe4.crmwebappandroid.models.customer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import no.usn.gruppe4.crmwebappandroid.R

class CustomerAdapter (private val context: Context, private val dataset: List<Customer>):RecyclerView.Adapter<CustomerAdapter.ItemViewHolder>(){
    private lateinit var customerListener : OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        customerListener = listener
    }

    class ItemViewHolder(val view: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        val textViewFirstname: TextView = view.findViewById<TextView>(R.id.custFirstname)!!
        val textViewLastname: TextView = view.findViewById<TextView>(R.id.custLastname)!!
        val textViewPhone: TextView = view.findViewById<TextView>(R.id.custPhone)!!
        val textViewEmail: TextView = view.findViewById<TextView>(R.id.custEmail)!!

        init {
            itemView.setOnClickListener{
                listener.onItemClick(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.employee_item, parent,false)
        return ItemViewHolder(adapterLayout, customerListener)
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