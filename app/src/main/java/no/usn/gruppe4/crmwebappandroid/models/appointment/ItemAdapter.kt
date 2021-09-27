package no.usn.gruppe4.crmwebappandroid.models.appointment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import no.usn.gruppe4.crmwebappandroid.R

class ItemAdapter(private val context: Context, private val dataset: List<Appointment>): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textViewTime: TextView = view.findViewById(R.id.calItemTime)
        val textViewCust: TextView = view.findViewById(R.id.calItemCustomer)
        val textViewServ: TextView = view.findViewById(R.id.calItemService)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        //create the new card
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.appointment_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textViewTime.text = context.resources.getString(item.time)
        holder.textViewCust.text = context.resources.getString(item.customer)
        holder.textViewServ.text = context.resources.getString(item.service)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}