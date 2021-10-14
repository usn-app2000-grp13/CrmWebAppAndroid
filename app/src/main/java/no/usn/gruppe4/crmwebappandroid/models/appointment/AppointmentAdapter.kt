package no.usn.gruppe4.crmwebappandroid.models.appointment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import no.usn.gruppe4.crmwebappandroid.R
import kotlin.math.min

class AppointmentAdapter(private val context: Context, private val dataset: List<Appointment>): RecyclerView.Adapter<AppointmentAdapter.ItemViewHolder>() {

    private lateinit var mlistener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mlistener = listener

    }

    class ItemViewHolder(val view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        val textViewTime: TextView = view.findViewById(R.id.calItemTime)
        val textViewCust: TextView = view.findViewById(R.id.calItemCustomer)
        val textViewServ: TextView = view.findViewById(R.id.calItemService)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        //create the new card
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.appointment_item, parent, false)
        return ItemViewHolder(adapterLayout, mlistener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val item = dataset[position]
        holder.textViewTime.text = timeIndexFormat(item.timeindex!!)
        holder.textViewCust.text = item.customers[0]._customer?.firstname + " " + item.customers[0]._customer?.lastname
        holder.textViewServ.text = item.services[0]._service?.name
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    /**
     * function for returning a time in hh:mm format from a timeindex
     */
    fun timeIndexFormat(timeindex: Int): String{
        var res = ""
        var clockM = timeindex % 60
        var clockH = (timeindex - clockM) / 60

        if (clockH < 10){
            res += "0$clockH:"
        }else{
            res += "$clockH:"
        }
        if (clockM < 10) {
            res += "0$clockM"
        }else{
            res += "$clockM"
        }
        return res
    }
}