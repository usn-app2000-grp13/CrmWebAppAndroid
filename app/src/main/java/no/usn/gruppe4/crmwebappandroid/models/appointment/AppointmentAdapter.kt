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

class AppointmentAdapter(private val context: Context, private val dataset: List<AppointmentResponse.Appointment>): RecyclerView.Adapter<AppointmentAdapter.ItemViewHolder>() {

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
        val hour = item.date?.hours!!
        val minutes = item.date?.minutes!!
        holder.textViewTime.text = formatTime(hour, minutes)
        holder.textViewCust.text = "item.comment"
        holder.textViewServ.text = "item.service"
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun formatTime(hour: Int, minutes: Int): String{
        var res = ""
        if (hour < 10){
            res += "0$hour:"
        }else{
            res += "$hour:"
        }
        if (minutes < 10){
            res += "0$minutes"
        }else{
            res += "$minutes"
        }
        return res
    }
}